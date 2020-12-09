package com.example.amigooculto;

import java.util.List;

public class Sortear {

    public static List<Cliente> sortear(List<Cliente> lista){
       // Cliente cliente = new Cliente();
                for (Cliente obj: lista)
                    obj.setAmigoOculto("AMIGO OCULTO");
                return lista;
    }


}
