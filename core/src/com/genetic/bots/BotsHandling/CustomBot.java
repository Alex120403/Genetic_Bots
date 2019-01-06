package com.genetic.bots.BotsHandling;

public class CustomBot extends Bot {
    boolean canMutate;

    // Statistic
    public int allSavedPepole, allExtinguishedFire;
    //public int id;

    protected CustomBot(Gene[] genes,String name,int sp,int ef) {
        super(genes);
        this.name = name;
        allSavedPepole = sp;
        allExtinguishedFire = ef;
    }

    @Override
    protected void mutateOneGene() {
        if(canMutate) {
            super.mutateOneGene();
        }
    }

    @Override
    void die(int fireX, int fireY) {
        super.die(fireX, fireY);
    }

    @Override
    public void addRescuedPeople() {
        super.addRescuedPeople();
        allSavedPepole++;
    }

    @Override
    public void addExtinguishedFire() {
        super.addExtinguishedFire();
        allExtinguishedFire++;
    }
}
