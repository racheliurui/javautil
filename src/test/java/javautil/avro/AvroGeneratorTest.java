package javautil.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.commons.io.IOUtils;
import org.junit.Test;



public class AvroGeneratorTest {	
	
	
	@Test
	public void TestSchemaParser() throws Exception  {
		String schemaString = IOUtils.toString(
			      this.getClass().getResourceAsStream("avroSchema.json"),
			      "UTF-8"
			    );

       Schema schema=AvroGenerator.getSchema(schemaString);
       
   	GenericRecord datum = AvroGenerator.getDatumBySchema(schema);
	File file = new File("target/data.avro");
	AvroGenerator.writeDatumToFile(datum,file,schema);
	//AvroGenerator.readDatumToString(file);
	DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>();
	DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, reader);
	GenericRecord result = dataFileReader.next();
	System.out.println("data" + result.get("username").toString());

	//result = dataFileReader.next();
	System.out.println("data :" + result.get("age").toString());
}


}
