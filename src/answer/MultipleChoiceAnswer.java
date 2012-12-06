package answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import question.QuestionManager;

public class MultipleChoiceAnswer extends Answer {

	public MultipleChoiceAnswer(int questionId, String answerText, double score) {
		super(questionId, new ArrayList<String>(Arrays.asList(new String[] {answerText})), 
				QuestionManager.MULTIPLE_CHOICE, -1, score);
	}
	
}
