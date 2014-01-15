package br.com.centralit.citgerencial.graph;

import java.text.NumberFormat;

import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;

import br.com.citframework.util.UtilFormatacao;

public class CustomLabelGenerator extends AbstractCategoryItemLabelGenerator
					implements CategoryItemLabelGenerator{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 8066236564383155148L;

	/**The threshold.*/
	//private double threshold;

	public CustomLabelGenerator(){
		super("",NumberFormat.getInstance());
		//this.threshold=threshold;
	}

	public String generateLabel(CategoryDataset dataset, int series, int category){
		String result=null;
		/*
		Number value=dataset.getValue(series,category);
		if(value!=null){
			double v=value.doubleValue();
			//return value.toString();
			//if(v>=this.threshold){
			result = UtilFormatacao.formatDouble(new Double(v), 2);
			//}
		}
		*/
		return result;
	}


}
