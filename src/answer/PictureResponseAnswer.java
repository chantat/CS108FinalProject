package answer;

import java.util.ArrayList;
import question.*;

public class PictureResponseAnswer extends Answer {
	public PictureResponseAnswer(int questionId, ArrayList<String> answerList) {
		super(questionId, answerList, QuestionManager.PICTURE_RESPONSE, -1, 1);
	}
}
