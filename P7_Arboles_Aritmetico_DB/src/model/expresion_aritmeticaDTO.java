package model;

public class expresion_aritmeticaDTO {
    private int id;
    private String expresion_aritmetica;

    public expresion_aritmeticaDTO(int id, String expresion_aritmetica) {
        this.id = id;
        this.expresion_aritmetica = expresion_aritmetica;
    }

    public int getId() {
        return id;
    }

    public String getExpresion() {
        return expresion_aritmetica;
    }

    public void setEAritmetica(String expresion_aritmetica) {
        this.expresion_aritmetica = expresion_aritmetica;
    }

    @Override
    public String toString() {
        return "Expresion Aritmetica: id = " + id + " | nombre = " + expresion_aritmetica;
    }
}
