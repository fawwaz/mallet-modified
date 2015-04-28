package cc.mallet.share.fawwaz;

import cc.mallet.pipe.Pipe;
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
		
		
		return carrier;
	}
}
