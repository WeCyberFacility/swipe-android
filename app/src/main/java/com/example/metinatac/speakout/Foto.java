package com.example.metinatac.speakout;

public class Foto {


        private String id;
        private String mName;
        private String mImageUrl;


        public Foto(){



        }

        public Foto(String id, String name, String imageUrl) {

            if(name.trim().equals("")) {

                name = "No Name";
                this.mImageUrl = imageUrl;
                this.id = id;
            }


            this.id = id;
            mName = name;
            mImageUrl = imageUrl;

        }

        public String getmName() {
            return mName;
        }

        public void setmName(String name) {
            mName = name;
        }

        public String getmImageUrl() {
            return mImageUrl;
        }

        public void setmImageUrl(String ImageUrl) {
            mImageUrl = ImageUrl;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
