/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Jeferson Pacheco
 */
public class Sugestao {
    
    private String email;
    private String utilizacao;
    private String nomePlanta;
    private String id;
    private String local;
    private Date data;

    public String getData() {
        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        return spd.format(data);
    }

    public void setData(Date data) {
        this.data = data;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUtilizacao() {
        return utilizacao;
    }

    public void setUtilizacao(String utilizacao) {
        this.utilizacao = utilizacao;
    }

    public String getNomePlanta() {
        return nomePlanta;
    }

    public void setNome(String nomePlanta) {
        this.nomePlanta = nomePlanta;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
