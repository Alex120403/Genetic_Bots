package com.genetic.bots.BotsHandling;

public class CustomBot extends Bot {
    boolean canMutate;

    // Statistic
    private long allSavedPepole, allExtinguishedFire;

    protected CustomBot(Gene[] genes) {
        super(genes);
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
