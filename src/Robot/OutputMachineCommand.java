package Robot;

import Simulation.Robots;

import java.util.Map;
import java.util.Scanner;

public class OutputMachineCommand {
    Map<String, Robot> robots;
    private String command;
    private String[] target;
    private String[] obstacle = {"1,1,1"};
    Scanner scanner = new Scanner(System.in);

    public OutputMachineCommand(String command, Map<String, Robot> robots) {
        this.command = command;
        this.robots = robots;
    }

    public Map<String, Robot> action() {


        command = command.replace(".", "");
        String[] grammar = command.split("_");
        String string = "";

        System.out.println(command);
        if (grammar[0].equals("0")) {
            for (int i = 1; i <= 10; i++) {
                Robot entity = robots.get(Integer.toString(i));
                if (entity.getState() == "Deactivated")
                    entity.setState("Activated");
                else
                    entity.setState("Deactivated");
                robots.put(Integer.toString(i), entity);
            }
            return robots;
        } else if (grammar[1].equals("-1")) {
            grammar[1] = "1,2,3,4,5,6,7,8,9,10";
        } else if (grammar[1].contains("=") || grammar[1].contains("<") || grammar[1].contains(">") || grammar[1].contains("!")) {
            String[] temp = grammar[1].split("&");
            for (int i = 0; i < temp.length; i++) {
                for (int j = 1; j <= 10; j++) {
                    Robot robot = robots.get(Integer.toString(j));
                    if (conditionCheck(robot, temp[i]))
                        string = string + j + ",";
                }
            }
            grammar[1] = string;
        } else if (grammar[1].contains("-") && !grammar[1].equals("-1")) {
            String[] temp = grammar[1].split(",");
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].contains("-")) {
                    int a = Integer.parseInt(temp[i].substring(0, temp[i].indexOf("-")));
                    int b = Integer.parseInt(temp[i].substring(temp[i].indexOf("-") + 1));
                    for (int j = a; j <= b; j++) {
                        string = string + j + ",";
                    }
                } else {
                    string = string + temp[i] + ",";
                }
            }
            grammar[1] = string;
        }
        target = grammar[1].split(",");
        for (int i = 1; i <= target.length; i++) {
            String[] location = {"0", "0", "0"};
            Robot entity = robots.get(target[i - 1]);

            if (entity.getState() == "Deactivated" || entity.getState() == "Failure" || entity.getState() == "Lost")
                continue;
            switch (grammar[0]) {
                case "1":
                    entity.setD(Integer.parseInt(grammar[2]));
                    robots.put(target[i - 1], entity);
                    continue;
                case "2":
                    robots.put(target[i - 1], movement(entity, location));
                    continue;
                    /*
                    int[] base = entity.getBase();
                    entity.setX(entity.base[0]);
                    entity.setY(entity.base[1]);
                    entity.setZ(entity.base[2]);
                    robots.put(Integer.toString(i), entity);
                    continue;
                     */
                case "3":
                    location = grammar[2].split(",");
                    robots.put(target[i - 1], movement(entity, location));
                    continue;
                case "4":
                    location = grammar[2].split(",");
                    while (Math.abs(entity.getX() - Integer.parseInt(location[0])) < entity.getD()) {
                        if (entity.getX() - Integer.parseInt(location[0]) >= 0) entity.setX(entity.getX() + 1);
                        else if (entity.getX() - Integer.parseInt(location[0]) < 0) entity.setX(entity.getX() - 1);
                    }

                    while (Math.abs(entity.getY() - Integer.parseInt(location[1])) < entity.getD()) {
                        if (entity.getY() - Integer.parseInt(location[1]) >= 0) entity.setY(entity.getY() + 1);
                        else if (entity.getY() - Integer.parseInt(location[1]) < 0) entity.setY(entity.getY() - 1);
                    }
                    while (Math.abs(entity.getZ() - Integer.parseInt(location[2])) < entity.getD()) {
                        if (entity.getZ() - Integer.parseInt(location[2]) >= 0) entity.setZ(entity.getZ() + 1);
                        else if (entity.getZ() - Integer.parseInt(location[2]) < 0) entity.setZ(entity.getZ() - 1);
                    }
                    robots.put(target[i - 1], entity);
                    continue;
                case "5":
                case "6":
                    location = grammar[2].split(",");
                    String[] secondLocation = {Integer.toString(entity.getX()), Integer.toString(entity.getY()), Integer.toString(entity.getZ())};
                    for (int j = 0; j < Integer.parseInt(grammar[3]); j++) {
                        entity = movement(entity, location);
                        System.out.println("\n\nRobot No." + target[i - 1] + "\n");
                        System.out.println("   X coordinate :" + entity.getX());
                        System.out.println("   y coordinate :" + entity.getY());
                        System.out.println("   z coordinate :" + entity.getZ());
                        String letter = scanner.nextLine();
                        if (letter.equals("STOP.") || letter.equals("stop.")) break;
                        else if (letter.equals("")) {
                            entity = movement(entity, secondLocation);
                            System.out.println("\n\nRobot No." + target[i - 1] + "\n");
                            System.out.println("   X coordinate :" + entity.getX());
                            System.out.println("   y coordinate :" + entity.getY());
                            System.out.println("   z coordinate :" + entity.getZ());
                            letter = scanner.nextLine();
                        } else {
                            System.out.println("Incorrect Input Value.");
                        }
                        if (letter.equals("STOP.") || letter.equals("stop.")) break;
                        else if (letter.equals("")) continue;
                        else {
                            System.out.println("Incorrect Input Value.");
                        }
                    }
                case "7":
                    location = grammar[2].split(",");
                    robots.put(target[i - 1], movement(entity, location));
                    continue;
                    /*
                    location = grammar[2].split(",");
                    if (location[0] != "-")
                        entity.setX(Integer.parseInt(location[0]));
                    if (location[1] != "-")
                        entity.setY(Integer.parseInt(location[1]));
                    if (location[2] != "-")
                        entity.setZ(Integer.parseInt(location[2]));
                    robots.put(target[i - 1], entity);
                    continue;

                     */
                case "8":
                    System.out.println("\n\nRobot No." + target[i - 1] + "\n");
                    System.out.println("   X coordinate :" + entity.getX());
                    System.out.println("   y coordinate :" + entity.getY());
                    System.out.println("   z coordinate :" + entity.getZ());
                    System.out.println("   State :" + entity.getState());
                    System.out.println("   Camera :" + entity.getCamera());
                    System.out.println("   Inventory :" + entity.getFunction());
                    System.out.println("   Distance :" + entity.getD());
                    System.out.println("   Light :" + entity.getLight());
                    System.out.println("   Rotation : Horizontal - " + entity.getCoordinate()[0] + " Vertical - " + entity.getCoordinate()[1]);
                    continue;
                case "9":
                    if (entity.getLight() == "Off")
                        entity.setLight("On");
                    else
                        entity.setLight("Off");
                    robots.put(target[i - 1], entity);
                    continue;
                case "10":
                    if (entity.getFunction() == "Off")
                        entity.setFunction("On");
                    else
                        entity.setFunction("Off");
                    robots.put(target[i - 1], entity);
                    continue;
                case "11":
                    entity.setCamera("On");
                case "12":
                    entity.setCamera("On");
                case "13":
                    String[] temp = grammar[grammar.length - 1].split(",");
                    int a = Integer.parseInt(temp[0]);
                    int b = Integer.parseInt(temp[1]);
                    if (a < 0) a = a + 360;
                    if (a > 360) a = a - 360;
                    if (b < 0) b = b + 360;
                    if (b > 360) b = b - 360;
                    int[] coordinate = {a, b};
                    entity.setCoordinate(coordinate);
                    robots.put(target[i - 1], entity);
                    continue;
                case "14":
                    entity.setCamera("Off");
                    robots.put(target[i - 1], entity);
                    continue;
            }
        }
        return robots;
    }

    private Robot movement(Robot entity, String[] location) {
        int x = entity.getX();
        int y = entity.getY();
        int z = entity.getZ();
        if (location[0] == "-") location[0] = Integer.toString(x);
        if (location[1] == "-") location[1] = Integer.toString(y);
        if (location[2] == "-") location[2] = Integer.toString(z);
        int[] coordinate = {Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2])};
        int[] flag = new int[3];
        while (true) {
            if (coordinate[0] > x) flag[0] = +1;
            else if (coordinate[0] < x) flag[0] = -1;
            else if (coordinate[0] == 0) flag[0] = 0;
            if (coordinate[1] > y) flag[1] = +1;
            else if (coordinate[1] < y) flag[1] = -1;
            else if (coordinate[1] == 0) flag[1] = 0;
            if (coordinate[2] > z) flag[2] = +1;
            else if (coordinate[2] < z) flag[2] = -1;
            else if (coordinate[2] == 0) flag[2] = 0;
            if (sensor(flag, entity.getD(), entity)) {
                location[0] = Integer.toString(entity.getX());
                location[1] = Integer.toString(entity.getY() + 1);
                location[2] = Integer.toString(entity.getZ());
                entity = movement(entity, location);
            }
            if (flag[0] == 1 && coordinate[0] != x) x++;
            else if (flag[0] == -1 && coordinate[0] != x) x--;
            if (flag[1] == 1 && coordinate[1] != y) y++;
            else if (flag[1] == -1 && coordinate[1] != y) y--;
            if (flag[2] == 1 && coordinate[2] != z) z++;
            else if (flag[2] == -1 && coordinate[2] != z) z--;
            entity.setX(x);
            entity.setY(y);
            entity.setZ(z);
            if (coordinate[0] == x && coordinate[1] == y && coordinate[2] == z) {
                return entity;
            }
        }
    }

    private boolean sensor(int[] flag, int distance, Robot entity) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        for (int i = 0; i < obstacle.length; i++) {
            String[] split = obstacle[i].split(",");
            for (int j = 0; j < distance; j++) {
                if (flag[0] == 1) x++;
                else if (flag[0] == -1) x--;
                if (flag[1] == 1) y++;
                else if (flag[1] == -1) y--;
                if (flag[2] == 1) z++;
                else if (flag[2] == -1) z--;
                if (x == Integer.parseInt(split[0]) && y == Integer.parseInt(split[1]) && z == Integer.parseInt(split[2])) {
                    return true;
                }
            }
        }
        return false;
    }
         /*
            if (flag[0] == 1 && Integer.parseInt(split[0]) - entity.getX() <= 0) continue;
            else if(flag[0]==0 && Integer.parseInt(split[0])==entity.getX()) continue;
            else if (flag[0] == -1 && entity.getX() - Integer.parseInt(split[0]) <= 0) continue;
            if (flag[1] == 1 && Integer.parseInt(split[1]) - entity.getY() <= 0) continue;
            else if(flag[1]==0 && Integer.parseInt(split[1])==entity.getY()) continue;
            else if (flag[1] == -1 && entity.getY() - Integer.parseInt(split[1]) <= 0) continue;
            if (flag[2] == 1 && Integer.parseInt(split[2]) - entity.getZ() <= 0) continue;
            else if(flag[2]==0 && Integer.parseInt(split[2])==entity.getZ()) continue;
            else if (flag[2] == -1 && entity.getZ() - Integer.parseInt(split[2]) <= 0) continue;
            x = Integer.parseInt(split[0]) - entity.getX();
            y = Integer.parseInt(split[1]) - entity.getY();
            z = Integer.parseInt(split[2]) - entity.getZ();

             */

    private boolean conditionCheck(Robot robot, String string) {
        String[] check = string.split(",");
        for (int i = 0; i < check.length; i++) {
            char a = check[i].charAt(1);
            char b = check[i].charAt(2);
            String value = null;
            switch (check[i].charAt(0)) {
                case 'x':
                    value = Integer.toString(robot.getX());
                    break;
                case 'y':
                    value = Integer.toString(robot.getY());
                    break;
                case 'z':
                    value = Integer.toString(robot.getZ());
                    break;
                case '0':
                    if (Character.getNumericValue(b) != robot.getStateNo()) return false;
                    else return true;
                case '1':
                    if (Character.getNumericValue(b) != robot.getCameraNo())
                        return false;
                    else return true;
                case '2':
                    if (Character.getNumericValue(b) != robot.getLightNo()) return false;
                    else return true;
                case '3':
                    if (Character.getNumericValue(b) != robot.getFunctionNo()) return false;
                    else return true;
            }
            if (a == '<') {
                if (!(Integer.parseInt(value) < b)) {
                    return false;
                }
            } else if (a == '>') {
                if (!(Integer.parseInt(value) > b)) {
                    return false;
                }
            } else if (a == '=') {
                if (!(Integer.parseInt(value) > b)) {
                    return false;
                }
            }
        }
        return true;
    }


}
