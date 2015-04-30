<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Bar chart with data from MySQL using Highcharts</title>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script type="text/javascript">


		function getQuerystringNameValue()
		{
    			// For example... passing a name parameter of "name1" will return a value of "100", etc.
    			// page.htm?name1=100&name2=101&name3=102

    			name="testplan";	
    			var winURL = window.location.href;
			var queryStringArray = winURL.split("?");
			var queryStringParamArray = queryStringArray[1].split("&");
			var nameValue = null;

                for ( var i=0; i<queryStringParamArray.length; i++ )
		 {           
                       queryStringNameValueArray = queryStringParamArray[i].split("=");

                      if ( name == queryStringNameValueArray[0] )
		        {
		            nameValue = queryStringNameValueArray[1];
		        }                       
	         }

		    return "consulta.php?testplan="+nameValue;
		}
	

		$(document).ready(



		function() {

			var options = {
	            chart: {
	                renderTo: 'container',
	                type: 'bar'
	            },
	            title: {
	                text: 'Project Requests',
	                x: -20 //center
	            },
	            subtitle: {
	                text: '',
	                x: -20
	            },
	            xAxis: {
	                categories: []
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Requests'
	                },
	                labels: {
	                    overflow: 'justify'
	                }
	            },
	            tooltip: {
	                formatter: function() {
	                        return '<b>'+ this.series.name +'</b><br/>'+
	                        this.x +': '+ this.y;
	                }
	            },
	            legend: {
	                layout: 'vertical',
	                align: 'right',
	                verticalAlign: 'top',
	                x: -10,
	                y: 100,
	                borderWidth: 0
	            }, 
	            plotOptions: {
	                bar: {
	                    dataLabels: {
	                        enabled: true
	                    }
	                }
	            },
	            series: []
	        }
	        



	        $.getJSON(getQuerystringNameValue(), function(json) {
				options.xAxis.categories = json[0]['data'];
	        	options.series[0] = json[1];
	        	options.series[1] = json[2];
		        chart = new Highcharts.Chart(options);
	        });
	    });




		</script>
	    <script src="http://code.highcharts.com/highcharts.js"></script>
        <script src="http://code.highcharts.com/modules/exporting.js"></script>
	</head>
	<body>
		<div id="container" style="min-width: 400px; height: 800px; margin: 0 auto"></div>
	</body>
</html>
