import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;

public class PptToPDFConverter extends PptxToPDFConverter {

	private Slide[] slides;
	
	
	public PptToPDFConverter(String inputFilePath, String outputFilePath) {
		super(inputFilePath, outputFilePath);
	}

	@Override	
	protected Dimension processSlides() throws IOException{
		FileInputStream iStream = getInFileStream();
		SlideShow ppt = new SlideShow(iStream);
		Dimension dimension = ppt.getPageSize();
		slides = ppt.getSlides();
		iStream.close();
		return dimension;
	}
	
	@Override
	protected int getNumSlides(){
		return slides.length;
	}
	
	@Override
	protected void drawOntoThisGraphic(int index, Graphics2D graphics){
		slides[index].draw(graphics);
	}

}