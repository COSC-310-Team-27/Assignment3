import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Chatbot extends JFrame implements ActionListener {

    /**
     *
     */
    static JTextArea area = new JTextArea();
    JTextField field = new JTextField();
    JScrollPane sp;
    JButton send;
    LocalTime time = LocalTime.now();
    LocalDate date = LocalDate.now();
    Random random = new Random();

    Chatbot(String title) {
        super(title);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(187, 222, 251));
        field = new JTextField();
        send = new JButton("send");
        send.setFont(new Font("Serif", Font.BOLD, 25));
        send.setBackground(Color.white);
        send.setBounds(680, 520, 90, 35);
        add(send);
        //For Text area
        area.setEditable(false);
        area.setBackground(Color.white);
        add(area);
        area.setFont(new Font("Serif", Font.PLAIN, 20));
        //scrollbar
        sp = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setBounds(10, 20, 775, 470);
        add(sp);

        //For TextField
        field.setSize(680, 35);
        field.setLocation(10, 520);
        field.setForeground(Color.black);
        field.setFont(new Font("Serif", Font.BOLD, 25));
        add(field);

        send.addActionListener(this);
        getRootPane().setDefaultButton(send);


    }

    public void actionPerformed(ActionEvent e) {
        ParseNLP parse;
        String browseMovies = "movies";
        String browseBooks = "books";
        String trivia = "trivia";
        String request = "request";
        String objective = "";
        String sentiment = "Neural";
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        Chat chatBot = new Chat();
        Person user1 = new Person();
        PCA pca = new PCA(user1.getUserVector());

        area.append(chatBot.getStatement(0));
        area.append("\nWould you like to: browse books, browse movies, play trivia, or request an item?\n");
        String text = field.getText().toLowerCase();
        area.append("User : " + field.getText() + "\n");

        parse = new ParseNLP(field.getText());
        ArrayList<String> option = parse.getStringList();
        for (String s : option) {
            if (s.equalsIgnoreCase(browseMovies)) {
                area.append("You have selected: browse movies, is that right?\n");
                boolean yes = chatBot.testReaction(field.getText()); //can pass string here instead
                if (yes) {
                    objective = browseMovies;
                }
            } else if (s.equalsIgnoreCase(browseBooks)) {
                area.append("You have selected: browse books, is that right?");
                boolean yes = chatBot.testReaction(field.getText()); //can pass string here instead
                if (yes) {
                    objective = browseBooks;
                }
            } else if (s.equalsIgnoreCase(trivia)) {
                area.append("You have selected: trivia, is that right?");
                boolean yes = chatBot.testReaction(field.getText()); //can pass string here instead
                if (yes) {
                    objective = trivia;
                }
            } else if (s.equalsIgnoreCase(request)) {
                area.append("You have selected: request a specific item, is that right?");
                boolean yes = chatBot.testReaction(field.getText()); //can pass string here instead
                if (yes) {
                    objective = request;
                }
            }
            ;


                if (text.contains("time")) {
                    String ctime = new String();
                    if (time.getHour() > 12) {
                        int hour = time.getHour() - 12;
                        ctime = ctime + hour + ":" + time.getMinute() + ":" + time.getSecond() + " PM";
                    } else {

                        ctime = ctime + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + " AM";
                    }
                    area.append(ctime);


                } else if (text.contains("date") || text.contains("month") || text.contains("year") || text.contains("day")) {

                    String cdate = new String();
                    cdate = cdate + date.getDayOfWeek() + "," + date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear();
                    area.append(cdate);


                } else if (text.contains("clear") && (text.contains("screen") || text.contains("chat"))) {
                    area.append("wait a few second...");
                    area.setText("");
                } else {
                    try {
                        try {
                            URL url = new URL("https://google.co.in");
                            URLConnection connection = url.openConnection();
                            connection.connect();
                            area.append("Here some results for you ...");
                            Desktop.getDesktop().browse(java.net.URI.create("http://www.google.com/search?hl=en&q=" + text.replace(" ", "+") + "&btnG=Google+Search"));

                        } catch (Exception ee) {
                            area.append("Connect with internet connection for get better results...");
                        }

                    } catch (Exception eee) {
                        int num = random.nextInt(3);
                        if (num == 0) {

                            area.append("Sorry ,I can't understand you !");
                        } else if (num == 1) {
                            area.append("Sorry,I don't understand ");
                        } else if (num == 2) {
                            area.append("My apologies...I don't understand ");
                        }

                }

            }
        }
    }
}
/*
        public static void main (String[] args){

            // TODO Auto-generated method stub
            Chatbot cb = new Chatbot("ChatBot");
            cb.setSize(800, 605);
            cb.setLocation(50, 50);
*/



