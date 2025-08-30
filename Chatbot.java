import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

class Chatbot extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea ca = new JTextArea();
    private JTextField cf = new JTextField();
    private JButton b = new JButton();
    private JLabel l = new JLabel();
    private JScrollPane scrollPane;

    Chatbot() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
        f.setLayout(null);
        f.setSize(400, 400);
        f.getContentPane().setBackground(Color.cyan);
        f.setTitle("ChatBot");

        f.add(ca);
        f.add(cf);
        ca.setSize(320, 310);
        ca.setLocation(1, 1);
        ca.setBackground(Color.BLACK);

        cf.setSize(300, 20);
        cf.setLocation(1, 320);

        f.add(b);
        l.setText("SEND");
        b.add(l);
        b.setSize(400, 20);
        b.setLocation(300, 320);

        scrollPane = new JScrollPane(ca);
        scrollPane.setSize(320, 310);
        scrollPane.setLocation(1, 1);
        f.add(scrollPane);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = cf.getText().toLowerCase();
                ca.setForeground(Color.GREEN);
                ca.append("You--> " + text + "\n");
                cf.setText("");

                boolean matched = false;

                matched = checkAndReply(text, "hi", "Hi there");
                if (!matched) matched = checkAndReply(text, "hello", "Hello there");
                if (!matched) matched = checkAndReply(text, "how are you", "I'm good :). Thank you for asking");
                if (!matched) matched = checkAndReply(text, "your name", "I'm the Trending BINOD");
                if (!matched) matched = checkAndReply(text, "created you",
                        "Group of Brainware Students\nMD. Sahabuddin\nAbhi Sarkar\nDipanjan Kuila\nMegha Saha");
                if (!matched) matched = checkAndReply(text, "yourself",
                        "Hello! I'm just a virtual assistant\nDesigned to help answer questions,\nprovide information");
                if (!matched) matched = checkAndReply(text, "gender", "I'm Female. Don't try to flirt with me :)");
                if (!matched) matched = checkAndReply(text, "love you", "I'm feeling shy :) Love you too");
                if (!matched) matched = checkAndReply(text, "bye", "Too soon :( Anyways\nSTAY HOME STAY SAFE");

                if (!matched && text.contains("joke")) {
                    String[] jokesArray = {
                            "Why did the cookie go to the nurse? Because he felt crumby!",
                            "Why did the tomato turn red? Because it saw the salad dressing!",
                            "Knock knock. Who’s there? Boo. Boo who? Don't cry, I’m only joking!"
                    };
                    Random random = new Random();
                    int randomNumber = random.nextInt(jokesArray.length);
                    replyMeth("Here's a funny one:\n" + jokesArray[randomNumber]);
                    matched = true;
                }

                if (!matched && text.contains("date")) {
                    LocalDateTime currentTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                    String formattedTime = currentTime.format(formatter);
                    replyMeth("Today is " + formattedTime + "\nI think it will be a great day for you");
                    matched = true;
                }

                if (!matched) {
                    // Immediately print "I did not know everything"
                    replyMeth("I did not know everything.\nLet's look it up on the Internet.\n");

                    // Countdown timer (3, 2, 1)
                    new javax.swing.Timer(1000, new ActionListener() {
                        int count = 3;
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            if (count > 0) {
                                replyMeth("Opening browser in " + count + "...");
                                count--;
                            } else {
                                ((javax.swing.Timer) evt.getSource()).stop();
                                try {
                                    String encodedQuery = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
                                    String searchUrl = "https://www.google.com/search?q=" + encodedQuery;
                                    if (Desktop.isDesktopSupported()) {
                                        Desktop.getDesktop().browse(new URI(searchUrl));
                                    }
                                } catch (IOException | URISyntaxException s) {
                                    s.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        });
    }

    // Method for whole-word matching
    private boolean checkAndReply(String text, String keyword, String reply) {
        if (text.matches(".*\\b" + keyword + "\\b.*")) {
            replyMeth(reply);
            return true;
        }
        return false;
    }

    // Method to append chatbot messages
    public void replyMeth(String s) {
        ca.append("ChatBot--> " + s + "\n");
    }
}

public class Chatbot{
    public static void main(String[] args) {
        new Chatbot();
    }
}
