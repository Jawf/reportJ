# reportJ
Since JText 2.1.7 is the last commercial-free version, I wappered and provied a easy way to use JText 2.1.7 and HTML-style template for generating report.

## How to use:

#### 1. Add maven dependency:
		<dependency>
			<groupId>com.github.jawf</groupId>
			<artifactId>reportj</artifactId>
			<version>1.1</version>
		</dependency>
		


#### 2. Use singleton API calling examples:
1. Generate Pdf to Path:
```
XhtmlPdfGenerator.getInstance().generatePdfToPath("report/template/test.ftl", model, "d:/test.pdf");
```

2. Generate Pdf and stamper it, output to path:
```
InputStream is = XhtmlPdfGenerator.getInstance().generatePdfToInputStream("report/template/test.ftl", model);
			PdfWrappedStamper.getInstance().stampToPath(is, "d:/test_.pdf");
```

### 3. (Optional) If cannot download the jar, please add repo:
	<repositories>
		repository>
			<id>oss</id>
			<url>https://oss.sonatype.org/content/groups/public</url>
		</repository>
	</repositories>

## TODO:
1. Css file may not be rightly loaded in spring-boot project, please use style content within template file instead.


##Implementation for this project with Maven dependencies:

	<dependency>
		<groupId>com.lowagie</groupId>
		<artifactId>itext</artifactId>
		<version>2.1.7</version>
	</dependency>
	<!-- html template render -->
	<dependency>
		<groupId>org.xhtmlrenderer</groupId>
		<artifactId>flying-saucer-core</artifactId>
		<version>9.0.7</version>
	</dependency>
	<dependency>
		<groupId>org.xhtmlrenderer</groupId>
		<artifactId>flying-saucer-pdf</artifactId>
		<version>9.0.7</version>
	</dependency>
	
	<!-- freemarker engine -->
	<dependency>
		<groupId>org.freemarker</groupId>
		<artifactId>freemarker</artifactId>
		<version>${freemarker.version}</version>
	</dependency>


## JUnit Test in source can generate sample pdf as below

Sample 1:

![Sample 1](https://github.com/Jawf/reportJ/blob/master/src/test/resources/sample/sampleStampPdf1.png)

Sample 2:

![Sample 2](https://github.com/Jawf/reportJ/blob/master/src/test/resources/sample/sampleStampPdf2.png)

Sample 3:

![Sample 3](https://github.com/Jawf/reportJ/blob/master/src/test/resources/sample/sampleStampPdf3.png)

Sample 4:

![Sample 4](https://github.com/Jawf/reportJ/blob/master/src/test/resources/sample/sampleStampPdf4.png)

Sample 5:

![Sample 5](https://github.com/Jawf/reportJ/blob/master/src/test/resources/sample/sampleStampPdf5.png)

Sample 6:

![Sample 6](https://github.com/Jawf/reportJ/blob/master/src/test/resources/sample/sampleStampPdf6.png)
