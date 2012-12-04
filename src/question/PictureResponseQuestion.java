package question;



public class PictureResponseQuestion extends Question {

		private int numResponses;
		private String imageURL;
		
		public PictureResponseQuestion(int qID, String qText, String imageURL) {
			super(qID, qText);
			//this.qText = imageURL;
			this.imageURL = imageURL;
			type = 4;
			this.numAnswers=1;
		}
		
		public int getNumResponses() {
			return numResponses;
		}
		
		public String getURL() {
			return imageURL;
		}

}
