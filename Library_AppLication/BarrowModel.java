package com.zoho.LibraryApp;
class BarrowModel
{
        private String barrowed_date;
        private String return_date;
        
        public BarrowModel(String barrowed_date,String return_date)
        {
                    this.barrowed_date=barrowed_date;
                    this.return_date=return_date;
        }
        
        public String getBarrowDate()
        {
                return barrowed_date;
        }
        public String getReturnDate()
        {
                return return_date;             
        }
}