package quiz;


public class PictureResponseQuestion extends Question {

		private int numResponses;
		
		public PictureResponseQuestion(int qID, String imageURL) {
			super(qID, imageURL);
			//this.qText = imageURL;
			type = 4;
			this.numAnswers=1;
		}
		
		public int getNumResponses() {
			return numResponses;
		}

}
