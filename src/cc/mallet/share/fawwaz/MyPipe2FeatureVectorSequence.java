package cc.mallet.share.fawwaz;

import java.util.ArrayList;

import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;
import cc.mallet.types.LabelAlphabet;
import cc.mallet.types.LabelSequence;

public class MyPipe2FeatureVectorSequence extends Pipe{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1500218237684586833L;
	
	public MyPipe2FeatureVectorSequence(){
		super(new Alphabet(),new LabelAlphabet());
	}
	
	@Override
	public Instance pipe(Instance carrier) {
		Object inputData = carrier.getData();
		Alphabet features = getDataAlphabet();
		LabelAlphabet labels;
		LabelSequence target = null;
		System.out.println("do something please");
		System.out.println("Building Pipe");
		
		
		ArrayList<Pipe> pipelist = new ArrayList<>();
		Pipe mypipe = new SerialPipes(pipelist);
		
		
		return mypipe.pipe(carrier);
	}
	
	// --- Private functions ---
	private String[][] parseSentence(String sentence)
    {
      String[] lines = sentence.split("\n");
      String[][] tokens = new String[lines.length][];
      for (int i = 0; i < lines.length; i++)
        tokens[i] = lines[i].split(" ");
      return tokens;
    }
}
