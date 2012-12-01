package webpackage;

public class PictureResponseQuestion extends Question {

		private String imageURL;
		private int numResponses;
		
		public PictureResponseQuestion(int qID, String imageURL, int numResponses) {
			super(qID);
			this.imageURL = imageURL;
			this.numResponses = numResponses;
			type = 4;
		}
		
		public int getNumResponses() {
			return numResponses;
		}
		
		public String getImageURL() {
			return imageURL;
		}


}
