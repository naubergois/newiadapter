/*Copyright [2016] [Francisco Nauber Bernardo Gois]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package br.unifor.iadapter.jmeter;

import java.awt.*;
import java.io.Serializable;
import java.text.DecimalFormatSymbols;

/**
 * GraphPanelChart configuration and settings holder
 */
public class ChartSettings implements Serializable {
    // Default draw options - these are default values if no property is entered in user.properties
    // List of possible properties:
    // jmeterPlugin.drawGradient=(true/false)
    // jmeterPlugin.neverDrawFinalZeroingLines=(true/false)
    // jmeterPlugin.optimizeYAxis=(true/false)
    // jmeterPlugin.neverDrawCurrentX=(true/false)
    // jmeterPlugin.useRelativeTime=(true/false)
    // jmeterPlugin.csvSeparator=(.?)

    public final static int CHART_TYPE_DEFAULT = 0;
    public final static int CHART_TYPE_LINE = 1;
    public final static int CHART_TYPE_BAR = 2;
    public final static int CHART_TYPE_CSPLINE = 3;
    public final static int CHART_MARKERS_DEFAULT = 0;
    public final static int CHART_MARKERS_YES = 1;
    public final static int CHART_MARKERS_NO = 2;
    //Global config
    private boolean configNeverDrawFinalZeroingLines = false;
    private boolean configOptimizeYAxis = true;
    private boolean configNeverDrawCurrentX = false;
    private String configCsvSeparator;
    //Live settings
    private boolean drawGradient = true;
    private boolean drawFinalZeroingLines = false;
    private boolean drawCurrentX = false;
    private boolean useRelativeTime = true;
    private boolean preventXAxisOverScaling = false;
    private int hideNonRepValLimit = -1;
    private int maxPointPerRow = -1;
    private long forcedMaxY = -1;
    private boolean expendRows = false;
    //Chart type
    private int chartType = CHART_TYPE_DEFAULT;
    private int chartMarkers = CHART_MARKERS_DEFAULT;
    private float lineWidth = 1.0f;
    //Strokes
    // The stroke used to paint Graph's dashed lines
    private final static Stroke dashStroke = new BasicStroke(
            1.0f, // Width
            BasicStroke.CAP_SQUARE, // End cap
            BasicStroke.JOIN_MITER, // Join style
            10.0f, // Miter limit
            new float[]{
                    1.0f, 4.0f
            }, // Dash pattern
            0.0f); // Dash phase
    // The stroke to paint thick Graph rows
    private final static Stroke thickStroke = new BasicStroke(
            AbstractGraphRow.LINE_THICKNESS_BIG,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL);
    //the composite used to draw bars
    private final static AlphaComposite barComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

    public ChartSettings() {
        if (new DecimalFormatSymbols().getDecimalSeparator() == '.') {
            configCsvSeparator = ",";
        } else {
            configCsvSeparator = ";";
        }
    }

    //configuration accessors
    public String getConfigCsvSeparator() {
        return configCsvSeparator;
    }

    public void setConfigCsvSeparator(String configCsvSeparator) {
        this.configCsvSeparator = configCsvSeparator;
    }

    public void setConfigNeverDrawCurrentX(boolean configNeverDrawCurrentX) {
        this.configNeverDrawCurrentX = configNeverDrawCurrentX;
    }

    public void setConfigNeverDrawFinalZeroingLines(boolean configNeverDrawFinalZeroingLines) {
        this.configNeverDrawFinalZeroingLines = configNeverDrawFinalZeroingLines;
    }

    public boolean isConfigOptimizeYAxis() {
        return configOptimizeYAxis;
    }

    public void setConfigOptimizeYAxis(boolean configOptimizeYAxis) {
        this.configOptimizeYAxis = configOptimizeYAxis;
    }

    //settings accessors
    public boolean isDrawCurrentX() {
        return drawCurrentX;
    }

    public void setDrawCurrentX(boolean drawCurrentX) {
        this.drawCurrentX = drawCurrentX;
    }

    public boolean isDrawFinalZeroingLines() {
        return drawFinalZeroingLines;
    }

    public void setDrawFinalZeroingLines(boolean drawFinalZeroingLines) {
        this.drawFinalZeroingLines = drawFinalZeroingLines;
    }

    public boolean isDrawGradient() {
        return drawGradient;
    }

    public void setDrawGradient(boolean drawGradient) {
        this.drawGradient = drawGradient;
    }

    public int getHideNonRepValLimit() {
        return hideNonRepValLimit;
    }

    public void setHideNonRepValLimit(int hideNonRepValLimit) {
        this.hideNonRepValLimit = hideNonRepValLimit;
    }

    public boolean isPreventXAxisOverScaling() {
        return preventXAxisOverScaling;
    }

    public void setPreventXAxisOverScaling(boolean preventXAxisOverScaling) {
        this.preventXAxisOverScaling = preventXAxisOverScaling;
    }

    public boolean isUseRelativeTime() {
        return useRelativeTime;
    }

    public void setUseRelativeTime(boolean useRelativeTime) {
        this.useRelativeTime = useRelativeTime;
    }

    public int getMaxPointPerRow() {
        return maxPointPerRow;
    }

    public void setMaxPointPerRow(int maxPointPerRow) {
        this.maxPointPerRow = maxPointPerRow;
    }

    public long getForcedMaxY() {
        return forcedMaxY;
    }

    public void setForcedMaxY(long forcedMaxY) {
        this.forcedMaxY = forcedMaxY;
    }

    // initialisation
    public void enableDrawFinalZeroingLines() {
        drawFinalZeroingLines = !configNeverDrawFinalZeroingLines;
    }

    public void enableDrawCurrentX() {
        drawCurrentX = !configNeverDrawCurrentX;
    }

    public int getChartType() {
        return chartType;
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
    }

    public int getChartMarkers() {
        return chartMarkers;
    }

    public void setChartMarkers(int chartMarkers) {
        this.chartMarkers = chartMarkers;
    }

    public Stroke getDashStroke() {
        return dashStroke;
    }

    public Stroke getThickStroke() {
        return thickStroke;
    }

    public AlphaComposite getBarComposite() {
        return barComposite;
    }

    public boolean isExpendRows() {
        return expendRows;
    }

    public void setExpendRows(boolean expendRows) {
        this.expendRows = expendRows;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
}
