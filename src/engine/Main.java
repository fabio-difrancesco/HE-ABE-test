package engine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.Cipher;

import HomomorphicEncryption.EncryptedValue;
import HomomorphicEncryption.EncryptionParameters;
import entities.AbstractExam;
import entities.EncryptedExam;
import entities.Exam;
import entities.Person;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import src.cpabe.bsw.ABECipher;
import src.cpabe.bsw.accessStructure.AccessTree;
import src.cpabe.bsw.common.ABEPrivateKey;
import src.cpabe.bsw.common.PublicKey;
import src.cpabe.bsw.ct.ABECiphertext;
import src.detabe.DETABECipher;
import src.utilABE.Files;

public class Main {

	public static void main(String[] args) throws Exception {

		Person p1 = new Person("John", "Doe");
		Person p2 = new Person("Jane", "Doe");
		
		Exam e1 = new Exam("Colesterolo", BigInteger.valueOf(150));
		Exam e2 = new Exam("Trigliceridi", BigInteger.valueOf(70));
		Exam e3 = new Exam("Glicemia", BigInteger.valueOf(100));
		Exam e4 = new Exam("Globuli bianchi", BigInteger.valueOf(4));
		

		EncryptionParameters params = new EncryptionParameters(256, 10);
		BigInteger privateKey = params.getSecretKey();
		BigInteger publicKey = params.getPublicKey();
		
		EncryptedValue v1 = new EncryptedValue(e1.getValue(), params);
		EncryptedValue v2 = new EncryptedValue(e2.getValue(), params);
		EncryptedValue v3 = new EncryptedValue(e3.getValue(), params);
		EncryptedValue v4 = new EncryptedValue(e4.getValue(), params);
		
		EncryptedExam ec1 = new EncryptedExam(e1.getType(), v1);
		EncryptedExam ec2 = new EncryptedExam(e2.getType(), v2);
		EncryptedExam ec3 = new EncryptedExam(e3.getType(), v3);
		EncryptedExam ec4 = new EncryptedExam(e4.getType(), v4);
		
		p1.setExams(Arrays.asList(ec1, ec2));
		p2.setExams(Arrays.asList(ec3, ec4));
		
		for (AbstractExam exam : p1.getExams()) 
		{
			System.out.println(exam.toString());
		}
		
		for (AbstractExam exam : p2.getExams()) 
		{
			System.out.println(exam.toString());
		}
		
		System.out.println("Decrypted values");
		for (AbstractExam exam : p1.getExams()) 
		{			
			Exam decryptedExam = new Exam(exam.getType(), ((EncryptedExam)exam).getEncryptedValue().decrypt(privateKey));
			System.out.println(decryptedExam.toString());
		}
		
		for (AbstractExam exam : p2.getExams()) 
		{
			Exam decryptedExam = new Exam(exam.getType(), ((EncryptedExam)exam).getEncryptedValue().decrypt(privateKey));
			System.out.println(decryptedExam.toString());
		}
		
		/*** jpbc test
		 */
		Pairing pairingTest = PairingFactory.getPairing("src/curves/a1.properties");
		Field G1 = pairingTest.getFieldAt(1);
		Element element = G1.newElement();
		element.setToRandom();
		Element element2 = G1.newElement();
		element2.setToZero();
		element.mul(element2);
        
		int secLevel = 128;
	      String type = "F";
	      final int MAX_ITERs = 1;
	      
	      int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
	      System.out.println(maxKeyLen);
	      
	      PublicKey PK = new PublicKey(secLevel,type);  // 
	      src.trustedAuth.bsw.MasterKey MK = new src.trustedAuth.bsw.MasterKey(secLevel,type);  // 
	      
	      System.out.println("ABE-PK: \n" + PK);
	      System.out.println("ABE-MK: \n" + MK);
	               
	      Pairing pairing = PK.e;
	            
	      double[][] timing = new double[MAX_ITERs][8];
	   
	          
	      String policy1 = "A";
	      String policy2 = "A B 2of2";
	      String policy4 = "A B 1of2 C D 1of2 1of2";
	      String policy6 = "A B 1of2 C D 1of2 E F 1of2 1of2 1of2";
	      String policy8 = "A B 1of2 C D 1of2 1of2 E F 1of2 G H 1of2 1of2 1of2";
	      String policy10 = "A B 1of2 C D 1of2 1of2 E F 1of2 G H 1of2 1of2 I J 1of2 1of2 1of2";
	      
	      //String[] policies = {policy1,policy2,policy4,policy6,policy8,policy10};
	      String[] policies = {policy10};
	      
	      
	      List<String> attributes = new LinkedList<String>();
	      attributes.add("A");
	      attributes.add("B");
	            
	      List keyList = null;
	      List decryptedList = null;
	      
	      for(String policy:policies){
	               
	         AccessTree tree = new AccessTree(policy);  
	      
	         if(!tree.isValid()){
	            System.out.println("DET-ABE ENCRYPTION LOCAL MODULE: Given policy is not valid (bad boolean equation). Program ends." );
	            System.exit(0);
	         }
	         
	         if(!tree.checkSatisfy(attributes)){
	            System.out.println("DET-ABE ENCRYPTION LOCAL MODULE: Given attribute set does not satisfy the encryption." );
	            System.exit(0);
	         }
	         
	         System.out.println("List " + attributes + " satisfy the tree"); 
	      
	      
	      
	         System.out.println("Tree: \n" + tree);   
	      
	         decryptedList = new ArrayList();
	         keyList = new ArrayList();
	      
	         for (int i = 0; i < MAX_ITERs; i++){
	            
	         //1. Creates the AES private key 
	            src.symmetric.AESCipher.genSymmetricKey(secLevel);
	         
	         //2. Encrypts the AES key with ABE. The secLevel and type 
	            ABECipher cipher = new ABECipher();
	            ABECiphertext ct = cipher.encrypt(PK, src.symmetric.AESCipher.key, tree);
	         
	         //3. Asks the private key for a user with the given attribs
	            ABEPrivateKey prv = src.trustedAuth.bsw.ABETrustedAuthority.keyGen(PK,MK,attributes);
	         
	         
	            prv.show();
	            ct.show();
	         }
	      }
	      
	      detabeTest();

	}
	
	public static void detabeTest()
	{
		String policy1 = "A B 1of2";
	      
	      AccessTree tree = new AccessTree(policy1);       
	      System.out.println("Tree: \n" + tree);   
	      
	      //check for satisfisibility
	      List<String> attributes = new LinkedList<String>();
	      attributes.add("manager");
	      attributes.add("professor");
	      attributes.add("A");
	   
	      System.out.println("List " + attributes + " satisfy tree? " + tree.checkSatisfy(attributes));   
	      String filepln = "src/test/results/file.pdf";
	      String fileenc = "src/test/results/file.pdf.encABE";
	      String filedec = "src/test/results/file.pdf.encABE.dec.pdf";         
	      
	      byte datapln[] = Files.readBytesFromFile(filepln);
	   
	      String type = "A";
	      int secLevel = 128;
	   
	   
	      List list = encrypt(policy1,secLevel,type,datapln,1);
	      
	      Files.storeObject(list,fileenc, "Encryped DET-ABE data");
	      
	             //recover from file the CT and the encrypted data
	      list = (LinkedList)Files.readObject(fileenc, "DET-ABE DECRYPTION MODULE: Reading encryped DET-ABE data");
	      
	      byte datadec[] = decrypt(list, attributes,1);    
	      
	      Files.storeBytesInFile(datadec, filedec);     
	      
	      //muestra los resultados,
	      System.out.println("\nTime in seconds:\n sym-key-gen \t sym-enc \t abe-enc \t pack-det \t prv-dec-key \t abe-dec \t aes-dec");
	      for(int j = 0; j < 7; j++)
	         System.out.print(DETABECipher.timing[j] + ",\t");
	      System.out.println();            
	   
	   }
	  
	   public static byte[] decrypt(List list, List attributes, int ITERS){
	      double results[] = new double[ITERS];
	      DETABECipher cipher = new DETABECipher();
	      long startTime, endTime;
	      byte[] result = null;
	      
	      for (int i = 0; i < ITERS; i++){
	         startTime = System.nanoTime();  
	         result = cipher.decrypt(list,attributes);   
	         endTime = System.nanoTime();
	         results[i] = (double)(endTime - startTime)/1000000000.0;   
	      }
	      System.out.println("Decrypt time: ");
	      for(double d:results)
	         System.out.println(d);
	   
	      return result;
	   }
	   
	   
	   public static List encrypt(String policy, int secLevel, String type, byte[] data, int ITERS){
	      double results[] = new double[ITERS];
	      DETABECipher cipher = new DETABECipher();
	      long startTime, endTime;
	      List list = null;
	     
	      for (int i = 0; i < ITERS; i++){
	         startTime = System.nanoTime();
	         list = cipher.encrypt(data, secLevel,type, policy);
	         endTime = System.nanoTime();
	         results[i] = (double)(endTime - startTime)/1000000000.0;
	      }
	   
	      System.out.println("Encrypt time: ");
	      for(double d:results)
	         System.out.println(d);
	   
	      return list;
	   }

}
