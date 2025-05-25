package com.proyecto.db;

public class EmailRequest {
    private String para;
    private String asunto;
    private String mensaje;

    // Getters y setters
    public String getPara() {
        return para;
    }
    public void setPara(String para) {
        this.para = para;
    }
    public String getAsunto() {
        return asunto;
    }
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
