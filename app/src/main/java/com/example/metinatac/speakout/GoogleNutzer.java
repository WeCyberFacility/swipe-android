package com.example.metinatac.speakout;

public class GoogleNutzer {



        private String id;
        private String name;
        private String nachname;
        private String username;

        private String email;
        private int follower;

        public GoogleNutzer(String id, String name, String nachname, String username, String email, int follower) {

            this.id = id;
            this.name = name;
            this.nachname = nachname;
            this.username = username;

            this.email = email;
            this.follower = follower;


        }

        public GoogleNutzer () {

        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNachname() {
            return nachname;
        }

        public void setNachname(String nachname) {
            this.nachname = nachname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getFollower() {
            return follower;
        }

        public void setFollower(int follower) {
            this.follower = follower;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


