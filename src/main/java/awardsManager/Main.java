/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package awardsManager;

import static dao.DbConnection.connect;

/**
 *
 * @author Axwor
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Connect to database
        connect(); 
    }

}
