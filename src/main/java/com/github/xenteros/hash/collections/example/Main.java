package com.github.xenteros.hash.collections.example;

class Main {

    public static void main(String[] args) {
        Dog d1 = new FrancuskiPiesek();
        Dog d2 = new FrancuskiPiesek();
        Dog d3 = new Doberman();
        Dog d4 = new Doberman();

        Dog[] dogs = new Dog[] {d1, d2, d3, d4};
        for (Dog dog : dogs) {

        }


        FrancuskiPiesek[] francuskiePieski = new FrancuskiPiesek[2];
        francuskiePieski[0] = (FrancuskiPiesek) dogs[0];
        francuskiePieski[1] = (FrancuskiPiesek) dogs[1];

        Object one = 1;
        int o = (int) one;

        int i1 = 1;
        Integer i2 = 1;
        Integer i3 = i1;
        System.out.println(i3);
        i1 = i3;
    }
}
