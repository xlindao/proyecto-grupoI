package com.mycompany.grupoi.proyectogrupoi;

import com.mycompany.grupoi.proyectogrupoi.ui.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProyectoGrupoI {

    private static final Logger logger = LogManager.getLogger(ProyectoGrupoI.class);

    public static void main(String[] args) {
        logger.info("Iniciando TaskManager Grupo I");
        new Menu().start();
        logger.info("Aplicación finalizada");
    }
}
