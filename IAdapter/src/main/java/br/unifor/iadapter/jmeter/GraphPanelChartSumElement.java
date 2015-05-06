package br.unifor.iadapter.jmeter;




/** {@inheritDoc} */
public class GraphPanelChartSumElement
      extends AbstractGraphPanelChartElement
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int count = 0;
   private double sumValue = 0;

   /**
    *
    * @param yVal
    */
   public GraphPanelChartSumElement(double yVal)
   {
      add(yVal);
   }

   GraphPanelChartSumElement()
   {
   }

   /**
    *
    * @param yVal
    */
   public void add(double yVal)
   {
      sumValue += yVal;
      count++;
   }

   /** {@inheritDoc} */
   public double getValue()
   {
      return sumValue;
   }

   /**
    *
    * @return
    */
   public int getCount()
   {
      return count;
   }
}
