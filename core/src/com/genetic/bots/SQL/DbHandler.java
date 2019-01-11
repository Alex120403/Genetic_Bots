package com.genetic.bots.SQL;

import com.genetic.bots.BotsHandling.*;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.*;
import java.util.*;

public class DbHandler {

    // Константа, в которой хранится адрес подключения
    private static String CON_STR = "";

    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DbHandler
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private DbHandler() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        File file = new File("chromosomes.db");
        System.out.println(file.exists());
        CON_STR = "jdbc:sqlite:C:/Users/Alex/Documents/GitHub/GeneticBots/core/assets/chromosomes.db";
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public List<Chromosome> getAllProducts() {

        // Statement используется для того, чтобы выполнить sql-запрос
        try {
            Statement statement = this.connection.createStatement();
            // В данный список будем загружать наши продукты, полученные из БД
            List<Chromosome> products = new ArrayList<Chromosome>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT name, chromosome FROM bots");
            // Проходимся по нашему resultSet и заносим данные в products
            BotFactory bf = new BotFactory();
            while (resultSet.next()) {
                Gene[] genes = bf.parseChromosome(resultSet.getString("chromosome"));
                products.add(new Chromosome(genes,resultSet.getString("name")));
            }
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    // Добавление продукта в БД
    public void addBot(Bot bot) {
        //deleteProduct(5);
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO bots(`name`, `chromosome`) " +
                            "VALUES(?, ?)");
            //statement.setObject(1, bot.id);
            statement.setObject(1, bot.getName());
            statement.setObject(2, bot.getChromosomeForSQL());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление продукта по id
    public void deleteBot(int id) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "DELETE FROM bots WHERE id = ?");
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
