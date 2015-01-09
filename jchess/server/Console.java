/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess.server;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

class Console {

    public static void main(String[] args) {
        System.out.println("JChess Server Start!");

        Server server = new Server(); //create server
        Server.isPrintEnable = false;

        boolean isOK = true;
        while (isOK) {
            System.out.println("--------------------");
            System.out.println("[1] Nowy stół");
            System.out.println("[2] Lista aktywnych stołów");
            System.out.println("[3] Włącz/wyłącz komunikaty serwera");
            System.out.println("[4] Wyłącz serwer");
            System.out.print("-> ");
            String str = readString();

            switch (str) {
            case "1":  //new table
                System.out.print("ID gry: ");
                int gameID = Integer.parseInt(readString());

                System.out.print("Hasło: ");
                String pass = readString();

                String observer;
                do {
                    System.out.print("Gra z obserwatorami[t/n]: ");
                    observer = readString();
                } while (!observer.equalsIgnoreCase("t") && !observer.equalsIgnoreCase("n"));

                boolean canObserver = observer.equalsIgnoreCase("t");

                server.newTable(gameID, pass, canObserver, true); //create new table

                break;
            case "2":  //list of tables
                for (Map.Entry<Integer, Table> entry : Server.tables.entrySet()) {
                    Integer id = entry.getKey();
                    Table table = entry.getValue();

                    String p1, p2;

                    if (table.clientPlayer1 == null || table.clientPlayer1.nick == null) {
                        p1 = "empty";
                    } else {
                        p1 = table.clientPlayer1.nick;
                    }

                    if (table.clientPlayer2 == null || table.clientPlayer2.nick == null) {
                        p2 = "empty";
                    } else {
                        p2 = table.clientPlayer2.nick;
                    }

                    System.out.println("\t" + id + ": " + p1 + " vs " + p2);
                }
                break;
            case "3":  //on/off server's communicats
                if (!Server.isPrintEnable) {
                    Server.isPrintEnable = true;
                    System.out.println("Komunikaty serwera zostały włączone");
                } else {
                    Server.isPrintEnable = false;
                    System.out.println("Komunikaty serwera zostały wyłączone");
                }
                break;
            case "4":  //exit
                isOK = false;
                break;
            default:  //bad commant
                System.out.println("Nierozpoznane polecenie");
                break;
            }
        }
        System.exit(0);
    }

    private static String readString() { //read strings from console
        int ch;
        StringBuilder sb = new StringBuilder();
        try {
            while ((ch = System.in.read()) != 10) {
                sb.append((char) ch);
            }
        } catch (IOException ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sb.toString();
    }
}
