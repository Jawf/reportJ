# reportJ
Since JText 2.1.7 is the last commercial-free version, I wappered and provied a easy way to use JText 2.1.7 and HTML-style template for generating report.

## Maven dependencies:

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
	
## API calling examples:
1. Generate Pdf to Path:
```
XhtmlPdfGenerator.getInstance().generatePdfToPath("report/template/test.ftl", model, "d:/test.pdf");
```

2. Generate Pdf and stamper it, output to path:
```
InputStream is = XhtmlPdfGenerator.getInstance().generatePdfToInputStream("report/template/test.ftl", model);
			PdfWrappedStamper.getInstance().stampToPath(is, "d:/test_.pdf");
```

## JUnit Test can generate sample pdf as below


![Sample 1](https://github.com/Jawf/reportJ/src/test/resources/sample/sampleStampPdf1.png)
