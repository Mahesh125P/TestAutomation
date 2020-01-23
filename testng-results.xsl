<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html"/>
<xsl:template match="/">
    <html>
    	<head>
<style>
BODY
{
}

#TableTitle
{
	BORDER-RIGHT: #4f81bd 1px solid;
	BORDER-TOP: #4f81bd 1px solid;
	BORDER-LEFT: #4f81bd 1px solid;
	BORDER-BOTTOM: #4f81bd 1px solid;
	width: 100%;
	height: 56px;
	font-family: Arial, Helvetica, sans-serif;
   text-align: center;
   COLOR: black; 
	background-color: #d3dfee;
}

#TableTitle tr.header td
{
	padding-top: 8px;
	font-size: 24px;
}

#TableTitle tr td
{
	padding-top: 8px;
	padding-bottom: 8px;
	font-size: 18px;
}

#TableApplication
{
	font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
	font-size: 14px;
}

strong
{
	font-family: Arial, Helvetica, sans-serif;
	COLOR: #005ef4; 
	font-size: 12px;
	text-align: left;
}

span
{
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	font-size: 12px;
}

#TableSummary
{
  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
  width:80%;
  border-collapse:collapse;
}
#TableSummary tr.Head td
{
	text-align: center;
	FONT-WEIGHT: bold; 
	font-size: 14px;
	COLOR: #005ef4; 
	BORDER-RIGHT: #ff620d 1px solid;
	BORDER-TOP: #ff620d 1px solid;
	BORDER-LEFT: #ff620d 1px solid;
	BORDER-BOTTOM: #ff620d 1px solid;
	BACKGROUND-COLOR: #ffe3c8;	
}
#TableSummary tr.Count td
{
	text-align: center;
	font-size: 13px;
	BORDER-RIGHT: #ff620d 1px solid;
	BORDER-TOP: #ff620d 1px solid;
	BORDER-LEFT: #ff620d 1px solid;
	BORDER-BOTTOM: #ff620d 1px solid;
}

#SummaryOfTestCaseTable
{
  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
  width:80%;
  border-collapse:collapse;
}
#SummaryOfTestCaseTable tr.SummaryOfTestCaseTable_Head td
{
	text-align: center;
	FONT-WEIGHT: bold; 
	font-size: 14px;
	BORDER-RIGHT: #4f81bd 1px solid;
	BORDER-TOP: #4f81bd 1px solid;
	BORDER-LEFT: #4f81bd 1px solid;
	BORDER-BOTTOM: #4f81bd 3px solid;
	
}
#SummaryOfTestCaseTable tr.TotalFailed td
{
	text-align: center;
	font-size: 13px;
	BORDER-RIGHT: #4f81bd 1px solid;
	BORDER-TOP: #4f81bd 1px solid;
	BORDER-LEFT: #4f81bd 1px solid;
	BORDER-BOTTOM: #4f81bd 1px solid;
}
#SummaryOfTestCaseTable tr.TotalPassed td
{
	text-align: center;
	font-size: 13px;
	BORDER-RIGHT: #4f81bd 1px solid;
	BORDER-TOP: #4f81bd 1px solid;
	BORDER-LEFT: #4f81bd 1px solid;
	BORDER-BOTTOM: #4f81bd 1px solid;
	BACKGROUND-COLOR: #d3dfee;	
}


#TestSuite
{
   font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
	text-align: left;
	width: 100%;
	BORDER-RIGHT: #4f81bd 1px solid;
	BORDER-TOP: #4f81bd 1px solid;
	BORDER-LEFT: #4f81bd 1px solid;
	BORDER-BOTTOM: #4f81bd 1px solid;
	FONT-SIZE: 14px;
	margin-bottom: 1px;
}

.TestCase
{
	width: 100%;
	font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
	FONT-SIZE: 13px;
	background-color:#e3e8ff;
}

.MESSAGE
{
	FONT-WEIGHT: bold;
	width: 40%;
}

.DetailPassInfo
{
	COLOR: #009933;
	width: 80%;
}

.DetailFailInfo
{
	FONT-WEIGHT: bold; 
	COLOR: #ff3333;
	width: 70%;
}

.DetailWarnInfo
{
	COLOR: orange;
	width: 70%;
}

.Passed
{
	COLOR: #009933;
	width: 9%;
}

.Failed
{
	FONT-WEIGHT: bold; 
	COLOR: #ff3333;
	width: 9%;
}

.Warning
{ 
	COLOR: orange;
	width: 9%;
}
.Step
{
	font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	BORDER-RIGHT: #696969 0px solid;
	BORDER-TOP: #696969 0px solid;
	BORDER-LEFT: #696969 0px solid;
	BORDER-BOTTOM: #696969 0px solid;
	FONT-SIZE: 12px;
}

.M1
{		
	background-color: #f6f7ff;
}
.M2
{
	background-color: #FFFFFF;
}

.LinkToFile
{
	width: 4%;
}

A:link 
{
	text-decoration:none;
}
A:hover
{
	text-decoration: underline; 
	color: red;
}
A:visited 
{	
	text-decoration: none
}
A:active 
{
	text-decoration: none
}

.StepInfo
{
	width: 2%;
}
</style>
    		<script language="javascript" type="text/javascript">
<![CDATA[
                function toggleMenu(id,b)
    			{
    				if (document.getElementById)
    				{
    					var e = document.getElementById(id);
    					var b = document.getElementById(b);

    					if (e)
    					{
    						if (e.style.display != "block")
    						{
    							e.style.display = "block";
    							b.src="data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD13TdNiksLJv7K0542hjLSuRvOVBJI2EE/jz6ivO6KKAP/2Q==";
    						}
    						else
    						{
    							e.style.display = "none";
    							b.src="data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1ewsAw03/AIlVi8D2oaSR1GScJkk7Dzy2BnnnkYrgaKKAP//Z";
    						}
    					}
    				}
    			}
    			function expandall()
    			{
    				var e = document.all.tags("div");
    				var b = document.all.tags("img");

    				for (var i = 0; i < e.length; i++)
    				{
    					if (e[i].style.display == "none")
    					{
    						e[i].style.display = "block";
    					}
    				}
    				for (var i = 0; i < b.length; i++)
    				{
    					if (b[i].id != "m1" && b[i].id != "m2")
    					{
    						if (b[i].src.substring(b[i].src.lastIndexOf("_"), b[i].src.length) == "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1ewsAw03/AIlVi8D2oaSR1GScJkk7Dzy2BnnnkYrgaKKAP//Z")
    						{
    							b[i].src = "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD13TdNiksLJv7K0542hjLSuRvOVBJI2EE/jz6ivO6KKAP/2Q==";
    						}
    					}
    				}
    			}
    			function collapseall()
    			{
    				var e = document.all.tags("div");
    				var b = document.all.tags("img");

    				for (var i = 0; i < e.length; i++)
    				{
    					if(e[i].id != "Step" && e[i].id != "Summary" && e[i].id != "Application" && e[i].id != "TestSuite")
    					{
    						if (e[i].style.display == "block")
    						{
    							e[i].style.display = "none";
    						}
    					}
    				}
    				for (var i = 0; i < b.length; i++)
    				{
    					if (b[i].id != "m1" && b[i].id != "m2")
    					{
    						if (b[i].src.substring(b[i].src.lastIndexOf("_"), b[i].src.length) == "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD13TdNiksLJv7K0542hjLSuRvOVBJI2EE/jz6ivO6KKAP/2Q==")
    							{
    								b[i].src = "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1ewsAw03/AIlVi8D2oaSR1GScJkk7Dzy2BnnnkYrgaKKAP//Z";
    							}
    					}
    				}
    			}]]>
    		</script>
    	</head>
        <body>
            <table ID="TableTitle">
      			<tr Class ="header">
    				<td>
                        <xsl:value-of select="Report/@Header"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Automation Test Report
                    </td>
                </tr>
            </table>
            <br/>
            <div id="Application" Style="position: relative; display:block; text-align:center">
                <table ID="TableApplication">
          			<tr>
        				<td>
                            Application Name:
                        </td>
                        <td>
    					    <span><xsl:value-of select="Report/@ApplicationName"/></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Release:
                        </td>
                        <td>
    					    <span><xsl:value-of select="Report/@Release"/></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Build:
                        </td>
                        <td>
    					    <span><xsl:value-of select="Report/@Build"/></span>
                        </td>
                    </tr>
                </table>
            </div>
            <table>
    			<tr>
                    <td>
                        <strong>Start Time :</strong>
                    </td>
                    <td>
                        <span><xsl:value-of select="Report/@StartTime"/></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <strong>End Time :</strong>
                    </td>
                    <td>
                        <span><xsl:value-of select="Report/@EndTime"/></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <strong>Execute Time :</strong>
                    </td>
                    <td>
                        <span><xsl:value-of select="Report/@ExecuteHourTime"/><xsl:text> Hr(s) </xsl:text></span>
                        <span><xsl:value-of select="Report/@ExecuteMinuteTime"/><xsl:text> Min(s)</xsl:text></span>
                    </td>
                </tr>
    		</table>
            <br/>
            <div id="Summary" Style="position:relative; display:block; text-align:center">
                <table id="TableSummary" cellSpacing="0" cellPadding="0">
                    <tr class = "Head">
                        <td>
                            Summary of TestSuites
                        </td>
                        <td>
                            Passed
                        </td>
                        <td>
                            Failed
                        </td>
                    </tr>
                    <xsl:for-each select="Report/TestSuite">
                    <tr class = "Count">
                        <td><xsl:value-of select="@Desc"/></td>
                        <td><xsl:value-of select="count(TestCase[count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0  and count(Step[@Status='1']) != 0])"/></td>
                        <td><xsl:value-of select="count(TestCase[count(Step[@Status='2']) &gt; 0 or (count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0 and count(Step[@Status='1']) = 0)])" /></td>

                    </tr>
                    </xsl:for-each>
                    <tr class = "Count">
                        <td>Total</td>
                        <td><xsl:value-of select="count(Report/TestSuite/TestCase[count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0 and count(Step[@Status='1']) != 0])"/></td>
                        <td><xsl:value-of select="count(Report/TestSuite/TestCase[count(Step[@Status='2']) &gt; 0 or (count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0 and count(Step[@Status='1']) = 0)]) "/></td>
                    </tr>
                </table>
            </div>
            <br/>
    		<img src="data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1ewsAw03/AIlVi8D2oaSR1GScJkk7Dzy2BnnnkYrgaKKAP//Z" onClick="expandall()" id="m1"/><xsl:text> </xsl:text><a href="#" onClick="expandall()"><span>Expand All</span></a>
    		<xsl:text> </xsl:text>
            <img src="data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD13TdNiksLJv7K0542hjLSuRvOVBJI2EE/jz6ivO6KKAP/2Q==" onClick="collapseall()" id="m2"/><xsl:text> </xsl:text><a href="#" onClick="collapseall()"><span>Collapse All</span></a>
            <br/>
    	    <xsl:apply-templates select="Report/TestSuite">
    		</xsl:apply-templates>
            <br/>
            <xsl:variable name="lower">abcdefghijklmnopqrstuvwxyz</xsl:variable>
            <xsl:variable name="upper">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable>
            <xsl:for-each select="Report/TestSuite">
                <div id="SummaryOfTestCase" Style="position:relative; display:none; text-align:center">
          	</div>
            <br/>
    	    </xsl:for-each>
        </body>
    </html>
    </xsl:template>
	
	<xsl:template match="Report/TestSuite">
		<div id="TestSuite" Style="position:relative; display:block">
			<table cellSpacing="1" cellPadding="1"  onClick="toggleMenu('div{position()}.0', 'm{position()}.0')">
				<tr>
					<td class="message">
						<img id="m{position()}.0" src="data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1ewsAw03/AIlVi8D2oaSR1GScJkk7Dzy2BnnnkYrgaKKAP//Z" border="0"/>
						<xsl:text> </xsl:text><xsl:value-of select="@Desc"/>
                    </td>
                    <td class="Passed">Passed -
                        <xsl:value-of select ="count(TestCase[count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0  and count(Step[@Status='1']) != 0])"/>
                    </td>
                    <td class="Failed">Failed -
                        <xsl:value-of select ="count(TestCase[count(Step[@Status='2']) &gt; 0 or (count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0 and count(Step[@Status='1']) = 0)])"/>
                    </td>
                </tr>
			</table>
		</div>
		<div id="div{position()}.0" Style="display:none">
			<xsl:apply-templates select="TestCase">
			<xsl:with-param name="TestCasePosition" select="position()"/>
			</xsl:apply-templates>
		</div>
	</xsl:template>
	
	<xsl:template match="TestCase">
			<xsl:param name="TestCasePosition"/>
			<div id="TestCase" Style="left:10px; position: relative; display:block">
    			<table cellSpacing="1" cellPadding="1" align="center" Class="TestCase" onClick="toggleMenu('div{concat($TestCasePosition,'.',position())}', 'm{concat($TestCasePosition,'.',position())}')">
    				<tr>
    					<td class="message">
    						<img id="m{concat($TestCasePosition,'.',position())}" src="data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1ewsAw03/AIlVi8D2oaSR1GScJkk7Dzy2BnnnkYrgaKKAP//Z" border="0"/>
                            <xsl:text> </xsl:text><xsl:value-of select="@ID"/>
                            <xsl:text> </xsl:text><xsl:value-of select="@Desc"/>
                        </td>
    					 <xsl:element name="td">
                              <xsl:attribute name="class">
                                <xsl:if test="count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0 and count(Step[@Status='1']) != 0">
                                Passed
                                </xsl:if>
                                <xsl:if test="count(Step[@Status='2']) &gt; 0 or (count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0 and count(Step[@Status='1']) = 0)">
                                Failed
                                </xsl:if>
                                <xsl:if test="count(Step[@Status='2']) = 0 and count(Step[@Status='3']) != 0">
                                Warning
                                </xsl:if>
                			</xsl:attribute>
             			    <xsl:if test="count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0 and count(Step[@Status='1']) != 0">
                            	<xsl:text>Passed </xsl:text>
                            </xsl:if>
                            <xsl:if test="count(Step[@Status='2']) &gt; 0 or (count(Step[@Status='2']) = 0 and count(Step[@Status='3']) = 0 and count(Step[@Status='1']) = 0)">
                            	<xsl:text>Failed </xsl:text>
                            </xsl:if>
                            <xsl:if test="count(Step[@Status='2']) = 0 and count(Step[@Status='3']) != 0">
                            	<xsl:text>Warning </xsl:text>
                            </xsl:if>
    			          </xsl:element>
                    </tr>
    			</table>
			</div>
			<div id="div{concat($TestCasePosition,'.',position())}" Style="display:none">
			<xsl:apply-templates select="Step">
			</xsl:apply-templates>
			</div>
	</xsl:template>

	<xsl:template match="Step">
    	<div id="Step" Style="left:20px; position:relative; display:block">
    		<table cellSpacing="1" cellPadding="1" align="center" Class="Step">
			<xsl:element name="tr">
                    <xsl:if test="position()mod 2">
                        <xsl:attribute name="class">
                        M1
                        </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="not(position()mod 2)">
                        <xsl:attribute name="class">
                        M2
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:element name="td">
                        <xsl:attribute name="class">
                        <xsl:if test="@Status='1'">
                        Passed
			StepInfo
                        </xsl:if>
                        <xsl:if test="@Status='2'">
                        Failed
			StepInfo
                        </xsl:if>
                        <xsl:if test="@Status='3'">
                        Warning
			StepInfo
                        </xsl:if>
                        </xsl:attribute>
                        <xsl:text> </xsl:text>
                        <xsl:value-of select="."/>
                    </xsl:element>
                    
                    <xsl:if test="@Detail">
                        <xsl:element name="td">
                            <xsl:attribute name="class">
                            <xsl:if test="@Status='1'">
                            DetailPassInfo
                            </xsl:if>
                            <xsl:if test="@Status='2'">
                            DetailFailInfo
                            </xsl:if>
                            <xsl:if test="@Status='3'">
			    DetailWarnInfo
                            </xsl:if>
                            </xsl:attribute>
                            <xsl:text> </xsl:text><xsl:value-of select="@Detail"/>
                        </xsl:element>
                    </xsl:if>
                    
                    <xsl:if test="@ExpectedResult">
                        <xsl:element name="td">
                            <xsl:attribute name="class">
                            <xsl:if test="@Status='1'">
                            Passed
                            </xsl:if>
                            <xsl:if test="@Status='2'">
                            Failed
                            </xsl:if>
                            <xsl:if test="@Status='3'">
                            Warning
                            </xsl:if>
                            </xsl:attribute>
                            <xsl:text> </xsl:text><xsl:value-of select="@ExpectedResult"/>
                        </xsl:element>
                    </xsl:if>
        		    
                    <xsl:if test="@ActualResult">
                        <xsl:element name="td">
                            <xsl:attribute name="class">
                            <xsl:if test="@Status='1'">
                            Passed
                            </xsl:if>
                            <xsl:if test="@Status='2'">
                            Failed
                            </xsl:if>
                            <xsl:if test="@Status='3'">
                            Warning
                            </xsl:if>
                            </xsl:attribute>
                            <xsl:text> </xsl:text><xsl:value-of select="@ActualResult"/>
                        </xsl:element>
                    </xsl:if>
        		    
                    <xsl:if test="@ScreenShotPath">
                        <xsl:element name="td">
			    <xsl:if test="@ScreenShotPath=' '">
			    <xsl:attribute name="class">
                              LinkToFile
                            </xsl:attribute>
			     <xsl:text></xsl:text>
			    </xsl:if>
                            <xsl:if test="@ScreenShotPath!=' '">
			   <xsl:attribute name="class">
                            LinkToFile
                            </xsl:attribute>
                            <img height="50" width="50" onclick="window.open(this.src, '_self');" src="{@ScreenShotPath}" />
                           </xsl:if>
                        </xsl:element>
                    </xsl:if>

                    <xsl:if test="@Filepath">
                        <xsl:element name="td">
                            <xsl:attribute name="class">
                            LinkToFile
                            </xsl:attribute>
				<xsl:text>  </xsl:text><xsl:value-of select="@Filepath"/>    
                        </xsl:element>
                    </xsl:if>
    			</xsl:element>
    		</table>
    	</div>
	</xsl:template>

    <xsl:template name="GetFileName">
    <xsl:param name="filepath"/>
        <xsl:variable name="filename" select="substring-after($filepath,'\')"/>
        <xsl:choose>
        <xsl:when test="contains($filename,'\')">
          <xsl:call-template name="GetFileName">
              <xsl:with-param name="filepath" select="$filename"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
            <xsl:text> </xsl:text>
            <a href="{@Filepath}" target="_new"><xsl:value-of select="$filename"/></a>
        </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>