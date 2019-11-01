package javautil.avro;

import java.io.File;
import java.io.IOException;

import java.io.OutputStream;

import org.apache.avro.Schema;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

public class AvroGenerator {

	public int datasize;
	public String fileUrl;

	public static Schema getSchema(String schemaString) throws Exception {
		return new Schema.Parser().parse(schemaString);

	}

	public void write(Schema schema) throws IOException {

		GenericRecord datum = getDatumBySchema(schema);
		File file = new File("data.avro");
		writeDatumToFile(datum,file,schema);
		readDatumToString(file);
	}

	public static GenericRecord getDatumBySchema(Schema schema) {
		GenericRecord datum = new GenericData.Record(schema);
		datum.put("username", "zhangsan");
		datum.put("age", 10);
		return datum;

	}

	public static void writeDatumToFile(GenericRecord datum, File file, Schema schema) throws IOException {
		System.out.println("Written to avro data file");
		DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(writer);
		dataFileWriter.create(schema, file);
		dataFileWriter.append(datum);
		dataFileWriter.close();
	}

	public static void readDatumToString(File file) throws IOException {
		DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>();
		DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, reader);
		GenericRecord result = dataFileReader.next();
		System.out.println("data" + result.get("username").toString());

		//result = dataFileReader.next();
		System.out.println("data :" + result.get("age").toString());
	}
}
