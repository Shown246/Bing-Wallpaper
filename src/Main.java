import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        String wpPath = "/home/st/Pictures/Wallpapers/wallpaper.jpg";
        String os = System.getProperty("os.name");
        switch (os) {
            case "Windows 10":
                System.out.println("WINODWS DETECTED");
                break;
            case "Linux":
                String de = identifyDE();
                if (de == null) {
                    System.out.println("Couldn't identify your Desktop Environment"); // log Severe
                    break;
                }

                switch (de) {
                    case "xfce":
                        executeProcess("xfconf-query -c xfce4-desktop -p /backdrop/screen0/monitorVGA-1/workspace0/last-image -s \"" + wpPath + "\"");
                        break;
                    case "gnome":
                        executeProcess("gsettings set org.gnome.desktop.background picture-uri-dark \"file://" + wpPath + "\"");
                        break;
                    case "kde":
                        executeProcess("qdbus org.kde.plasmashell /PlasmaShell org.kde.PlasmaShell.evaluateScript 'var allDesktops = desktops();print (allDesktops);for (i=0;i<allDesktops.length;i++) {d = allDesktops[i];d.wallpaperPlugin = \"org.kde.image\";d.currentConfigGroup = Array(\"Wallpaper\", \"org.kde.image\", \"General\");d.writeConfig(\"Image\", \"" + wpPath + "\")}'");
                        break;
                    case "unity":
                        executeProcess("gsettings set org.gnome.desktop.background picture-uri \"file://" + wpPath + "\"");
                        break;
                    case "cinnamon":
                        executeProcess("gsettings set org.cinnamon.desktop.background picture-uri  \"file://" + wpPath + "\"");
                        break;
                    default:
                        System.out.println("Can't recognize DE: " + de);
                }


                break;
            default:
                System.out.println("Can't recognize OS: " + os);
        }
    }

    public static String identifyDE() {
        String de;
        de = System.getenv("XDG_CURRENT_DESKTOP").toLowerCase();

        if (de.contains("xfce")) {
            return "xfce";
        } else if (de.contains("kde")) {
            return "kde";
        } else if (de.contains("unity")) {
            return "unity";
        } else if (de.contains("gnome")) {
            return "gnome";
        } else if (de.contains("cinnamon")) {
            return "cinnamon";
        } else if (de.contains("mate")) {
            return "mate";
        } else if (de.contains("deepin")) {
            return "deepin";
        } else if (de.contains("budgie")) {
            return "budgie";
        } else if (de.contains("lxqt")) {
            return "lxqt";
        } else {
            System.out.println("Not identifiable with: echo $XDG_CURRENT_DESKTOP: " + de);
        }

        de = System.getenv("GDM_SESSION").toLowerCase();

        if (de.contains("xfce")) {
            return "xfce";
        } else if (de.contains("kde")) {
            return "kde";
        } else if (de.contains("unity")) {
            return "unity";
        } else if (de.contains("gnome")) {
            return "gnome";
        } else if (de.contains("cinnamon")) {
            return "cinnamon";
        }  else if (de.contains("mate")) {
            return "mate";
        } else if (de.contains("deepin")) {
            return "deepin";
        } else if (de.contains("budgie")) {
            return "budgie";
        } else if (de.contains("lxqt")) {
            return "lxqt";
        } else {
            System.out.println("Not identifiable with: echo $GDM_SESSION: " + de);
        }

        return null;
    }

    public static String executeProcess(String s) {
        System.out.println("Executing: " + s);
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", s);
        pb.redirectErrorStream(true);
        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder res = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return res.toString();
    }
}
