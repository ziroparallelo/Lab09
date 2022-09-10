
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private ComboBox<Country> cbxStati;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {

    	txtResult.clear();
    	//1. Controlli
    	
//    	1. Valore numerico
    	
    	String anno = txtAnno.getText();
    	Integer annoNum = null;
    	try {
    	annoNum = Integer.parseInt(anno);
    	} catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtResult.setText("ERRORE: Inserire un valore numerico");
    		return;
    	}
    	
//    	2. Il valore è stato inserito?
    	
    	if(annoNum==null || annoNum.intValue()==0) {
    		txtResult.setText("ERRORE: Nessun valore numerico inserito");
    		return;
    	}
    	
//    	3. E' nel range giusto
    	
    	int annoNumerico = annoNum.intValue();
    	if(annoNumerico<1816 || annoNumerico>2016) {
    		txtResult.setText("ERRORE: Inserire un valore numerico nel range(1816-2016) ");
    		return;
    	}
    	
    	this.model.creaGrafo(annoNumerico);
    	Map<Country, Integer> statiGrado = this.model.getStatiGrado(annoNumerico);
    	
    	for(Country c: statiGrado.keySet()) {
    		cbxStati.getItems().add(c);
    	}
    		
    	txtResult.appendText("Connettività grafo: "+this.model.getConnectivty()+"\n");
    	for(Country c: statiGrado.keySet())
    	{
    		
    		txtResult.appendText(c.getStateNme()+", "+statiGrado.get(c)+"\n");
    	}
    	return;
}
    

    @FXML
    void handleCercaStati(ActionEvent event) {

    	txtResult.clear();
    	
    	Country partenza = cbxStati.getValue();
    	
    	Set<Country> raggiungibili = model.esploraGrafo(partenza);
    	txtResult.appendText("Stati raggungibili: "+raggiungibili.size()+"\n");
    	for(Country c: raggiungibili) {
    		txtResult.appendText(c.getStateNme()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cbxStati != null : "fx:id=\"cbxStati\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    
    public void setModel(Model model) {
    	
    	this.model = model;
    }
}
