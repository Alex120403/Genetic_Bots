package com.genetic.bots.SQL;

import com.genetic.bots.BotsHandling.BotFactory;
import com.genetic.bots.BotsHandling.BotStatistics;
import com.genetic.bots.BotsHandling.CustomBot;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DbHandler {

    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:C:/Users/Alex/Documents/GitHub/GeneticBots/core/assets/db.db";

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
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public List<CustomBot> getAllProducts() {

        // Statement используется для того, чтобы выполнить sql-запрос
        try {
            Statement statement = this.connection.createStatement();
            // В данный список будем загружать наши продукты, полученные из БД
            List<CustomBot> products = new ArrayList<CustomBot>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bots");
            // Проходимся по нашему resultSet и заносим данные в products
            BotFactory bf = new BotFactory();
            while (resultSet.next()) {
               products.add(bf.createCustomBot(new BotStatistics(resultSet.getInt("id"),
                       resultSet.getInt("rescued_people"),
                       resultSet.getInt("extinguished_fire"),
                       resultSet.getString("chromosome"),
                       resultSet.getString("name"))));
            }
            // Возвращаем наш список
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    // Добавление продукта в БД
    public void addBot(CustomBot bot) {
        //deleteProduct(5);
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO bots(`name`, `chromosome`,`rescued_people`,`extinguished_fire`) " +
                            "VALUES(?, ?, ?, ?)");
            //statement.setObject(1, bot.id);
            statement.setObject(1, bot.getName());
            statement.setObject(2, bot.getChromosomeForSQL());
            statement.setObject(3, bot.allSavedPepole);
            statement.setObject(4, bot.allExtinguishedFire);
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
