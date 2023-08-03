package elecciones2021.web;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elecciones2021.dto.ResultDashboardDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import elecciones2021.form.CircuitoMesaForm;
import elecciones2021.model.AgrupacionPolitica;
import elecciones2021.model.CircuitoMesa;
import elecciones2021.model.Escrutinio;
import elecciones2021.model.Mesa;
import elecciones2021.model.Role;
import elecciones2021.model.User;
import elecciones2021.repository.AgrupacionPoliticaRepository;
import elecciones2021.repository.CircuitoMesaRepository;
import elecciones2021.repository.MesaRepository;
import elecciones2021.repository.RoleRepository;
import elecciones2021.repository.UserRepository;

@Controller
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = {"http://localhost:4200", "https://resultadostresdefebrero2021.com.ar","http://192.168.0.20:8080","http://192.168.1.9:8080","https://pasos2023-production.up.railway.app"})

@RequestMapping(value={"/client"})
public class AppController {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CircuitoMesaRepository circuitoMesaRepository;

	@Autowired
	private MesaRepository mesaRepository;
	
	@Autowired
	private AgrupacionPoliticaRepository agrupacionPoliticaRepository;
	
	
	@PersistenceContext
	private EntityManager entityManager;

	

	@RequestMapping(value = "/loadRoles", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Role> getAllRoles(){
		return roleRepository.findAll();
	}

	@RequestMapping(value = "/application", method = RequestMethod.GET)
	public ModelAndView  getFormCircuitoMesa(){
		ModelAndView responseModelAndView = new ModelAndView();
		CircuitoMesaForm cm = new CircuitoMesaForm();
		// cm.setMesa(null);
		responseModelAndView.getModel().put("circuitoMesaForm", new CircuitoMesaForm());
		responseModelAndView.setViewName("application");
		return responseModelAndView;
	}
	@RequestMapping(value = "/loadCircuitoMesa", method = RequestMethod.POST)
	public ModelAndView loadCircuitoMesa(@ModelAttribute("circuitoMesaForm") @Validated  CircuitoMesaForm circuitoMesaForm) {

		CircuitoMesa cm=circuitoMesaRepository.findCircuitoMesa(circuitoMesaForm.getCircuito(), circuitoMesaForm.getMesa()); 
		System.out.println(cm.getCircuito());
		ModelAndView responseModelAndView = new ModelAndView();
		responseModelAndView.getModel().put("circuito", cm.getCircuito());
		responseModelAndView.getModel().put("mesa", circuitoMesaForm.getMesa());
		responseModelAndView.setViewName("circuitoMesa");
		return responseModelAndView;
	}
	
	
    @RequestMapping(value={"/getInfoFromMesaCircuito"}, method={RequestMethod.GET}, produces={"application/json"})
    @ResponseBody
    public HashMap<String, Object> getInfoFromMesaCircuito(@RequestParam int mesa) {
        HashMap<String, Object> returnValue = new HashMap<String, Object>();
        //busco la mesa en el repositorio de mesas
        Mesa m = mesaRepository.findMesa(mesa);        
        if(m != null)
        {
        	returnValue.put("success", true);
        	/*Devuelve la mesa en cuestion*/
        	returnValue.put("mesa", m);
        }
        else 
        	returnValue.put("success", false);
        return returnValue;
    }
    
    @RequestMapping(value={"/loadPartidosPoliticos"}, method={RequestMethod.GET}, produces={"application/json"})
    @ResponseBody
    public List<AgrupacionPolitica> loadPartidosPoliticos() {
    	
    	return agrupacionPoliticaRepository.findAll();

    }


	@RequestMapping(value={"/getResults"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public ResultDashboardDTO getDataDTO() {

		ResultDashboardDTO responseResultDashboardDTO = ResultDashboardDTO.getResultDashboardDTO();
		responseResultDashboardDTO.setTotalTables(mesaRepository.getTotalMesas());
		responseResultDashboardDTO.setTotalTelegramsLoaded(mesaRepository.getTotalMesasCargadas());
		responseResultDashboardDTO.setTotalesxCircuito(mesaRepository.getTotalesXCircuito());
		responseResultDashboardDTO.setTotalesxDiputadosNacionales(mesaRepository.getTotalesDiputadosNacionales());
		responseResultDashboardDTO.setTotalesxLegisladoresProvinciales(mesaRepository.getTotalesLegisladoresProvinciales());
		responseResultDashboardDTO.setTotalesxConcejales(mesaRepository.getTotalesConcejales());
		//responseResultDashboardDTO.setTotalesxSenadores(mesaRepository.getTotalesSenadoresNacionales());

		return responseResultDashboardDTO;

	}


//	@Transactional
//	@MessageMapping("/updateMesa")
//	@SendTo("/topic/messages")
//	public ResultDashboardDTO updateMesa(@RequestBody HashMap<String,Object> dataRequest)
//	{
//		ResultDashboardDTO responseResultDashboardDTO = ResultDashboardDTO.getResultDashboardDTO();
//
//		HashMap<String, Object> returnValue = new HashMap<String,Object>();
//
//        Mesa m = mesaRepository.findMesa(Integer.valueOf(dataRequest.get("mesa").toString()));
//        if(m != null)
//        {
//        	responseResultDashboardDTO.setNroTable(Integer.valueOf(dataRequest.get("mesa").toString()));
//        	returnValue.put("existe_mesa", true);
//        	/*Actualizar la mesa*/
//
//             m.setFecha(new Date());
//
//        	 m.setTotalVotosXIndividuo(Integer.valueOf(dataRequest.get("ciudadanos_que_votaron_total").toString()));
//        	 m.setTotalsobres(Integer.valueOf(dataRequest.get("sobres_en_la_urna_total").toString()));
//
//        	 m.setTotalVotosDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_diputados_nacionales").toString()));
//        	 m.setTotalVotosLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_legisladores_provinciales").toString()));
//        	 m.setTotalVotosConcejales(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_concejales").toString()));
//
//        	 m.setTotalVotosNulosDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_nulos_diputados_nacionales").toString()));
//        	 m.setTotalVotosNulosLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_nulos_legisladores_provinciales").toString()));
//        	 m.setTotalVotosNulosConcejales(Integer.valueOf(dataRequest.get("total_votos_nulos_concejales").toString()));
//
//        	 m.setTotalVotosRecurridosDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_recurridos_diputados_nacionales").toString()));
//        	 m.setTotalVotosRecurridosLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_recurridos_legisladores_provinciales").toString()));
//        	 m.setTotalVotosRecurridosConcejales(Integer.valueOf(dataRequest.get("total_votos_recurridos_concejales").toString()));
//
//         	 m.setTotalVotosImpugnadoDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_impugnados_diputados_nacionales").toString()));
//        	 m.setTotalVotosImpugnadoLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_impugnados_legisladores_provinciales").toString()));
//        	 m.setTotalVotosImpugnadoConcejales(Integer.valueOf(dataRequest.get("total_votos_impugnados_concejales").toString()));
//
//        	 m.setTotalVotosBolsinDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_diputados_nacionales").toString()));
//        	 m.setTotalVotosBolsinLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_legisladores_provinciales").toString()));
//        	 m.setTotalVotosBolsinConcejales(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_concejales").toString()));
//
//        	 m.setTotalVotosBlancoDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_en_blanco_diputados_nacionales").toString()));
//        	 m.setTotalVotosBlancoLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_en_blanco_legisladores_provinciales").toString()));
//        	 m.setTotalVotosBlancoConcejales(Integer.valueOf(dataRequest.get("total_votos_en_blanco_concejales").toString()));
//
//        	 m.setTotalXColumnaDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_x_columna_diputados_nacionales").toString()));
//        	 m.setTotalXColumnaLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_x_columna_legisladores_provinciales").toString()));
//        	 m.setTotalVotosXColumnaConcejales(Integer.valueOf(dataRequest.get("total_votos_x_columna_concejales").toString()));
//
//
//        	 /*Generar los escrutinios para esta mesa*/
//
//
//        	 @SuppressWarnings("unchecked")
//			 List<HashMap<String,Object>> escrutinios_param = (List<HashMap<String,Object>>)dataRequest.get("escrutinio_detalle");
//
//        	 Iterator<HashMap<String,Object>> it_escrutinios = escrutinios_param.iterator();
//
//        	 Set<Escrutinio> escrutinios = new HashSet<Escrutinio>();
//
//        	 while(it_escrutinios.hasNext())
//        	 {
//        		 HashMap<String,Object> hm = it_escrutinios.next();
//
//        		 Escrutinio esc = new Escrutinio();
//
//        		 esc.setIdListaInterna(Integer.valueOf(hm.get("lista_interna").toString()));
//
//
//        		 esc.setTotalVotosDiputadosNacionales(Integer.valueOf(hm.get("diputados_nacionales").toString()));
//
//        		 esc.setTotalVotosLegisladoresProvinciales(Integer.valueOf(hm.get("legisladores_provinciales").toString()));
//
//        		 esc.setTotalVotosConcejales(Integer.valueOf(hm.get("concejales").toString()));
//
//        		 escrutinios.add(esc);
//
//        	 }
//        	 m.getEscrutinios().clear();
//
//        	 m.getEscrutinios().addAll(escrutinios);
//
//
//        	 mesaRepository.saveAndFlush(m);
//        }
//        else
//        	returnValue.put("existe_mesa", false);
//        
//        
//        
//
//		responseResultDashboardDTO.setTotalTables(mesaRepository.getTotalMesas());
//		
//		responseResultDashboardDTO.setTotalTelegramsLoaded(mesaRepository.getTotalMesasCargadas());
//		
//		
//		responseResultDashboardDTO.setTotalesxCircuito(mesaRepository.getTotalesXCircuito());
//		
//		responseResultDashboardDTO.setTotalesxDiputadosNacionales(mesaRepository.getTotalesDiputadosNacionales());
//		responseResultDashboardDTO.setTotalesxLegisladoresProvinciales(mesaRepository.getTotalesLegisladoresProvinciales());
//		responseResultDashboardDTO.setTotalesxConcejales(mesaRepository.getTotalesConcejales());
//		
//
//        return responseResultDashboardDTO;
//	}
	
	
	//"C:\\Users\\sergio\\Documents\\Paso2023.xlsx"
	
	@Transactional
	@RequestMapping(value = "/updateMesa", method = RequestMethod.POST, headers = {"Content-type=application/json"}, produces="application/json")
	
	public @ResponseBody Map<String, Object> updateMesa(@RequestBody HashMap<String,Object> dataRequest)
	{
	  HashMap<String, Object> returnValue = new HashMap<String,Object>();	
		
      Mesa m = mesaRepository.findMesa(Integer.valueOf(dataRequest.get("mesa").toString()));
      if(m != null)
      {
      	returnValue.put("existe_mesa", true);
      	/*Actualizar la mesa*/

           m.setFecha(new Date());

      	 m.setTotalVotosXIndividuo(Integer.valueOf(dataRequest.get("ciudadanos_que_votaron_total").toString()));
      	 m.setTotalsobres(Integer.valueOf(dataRequest.get("sobres_en_la_urna_total").toString()));

      	 m.setTotalVotosDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_diputados_nacionales").toString()));
      	 m.setTotalVotosLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_legisladores_provinciales").toString()));
      	 m.setTotalVotosConcejales(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_concejales").toString()));
      	 m.setTotalVotosIntendente(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_intendente").toString()));
      	 m.setTotalVotosGobernador(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_gobernador").toString()));
         m.setTotalVotosPresidente(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_presidente").toString()));
         m.setTotalVotosSenadores(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_senadores").toString()));
         m.setTotalVotosParlamentariosMercosur(Integer.valueOf(dataRequest.get("total_votos_agrupaciones_politicas_parlamentarios_mercosur").toString()));
         
         
      	 

      	 m.setTotalVotosNulosDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_nulos_diputados_nacionales").toString()));
      	 m.setTotalVotosNulosLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_nulos_legisladores_provinciales").toString()));
      	 m.setTotalVotosNulosConcejales(Integer.valueOf(dataRequest.get("total_votos_nulos_concejales").toString()));
      	 m.setTotalVotosNulosIntendente(Integer.valueOf(dataRequest.get("total_votos_nulos_intendente").toString()));
      	 m.setTotalVotosNulosGobernador(Integer.valueOf(dataRequest.get("total_votos_nulos_gobernador").toString()));
      	 m.setTotalVotosNulosPresidente(Integer.valueOf(dataRequest.get("total_votos_nulos_concejales").toString()));
      	 m.setTotalVotosNulosParlamentariosMercosur(Integer.valueOf(dataRequest.get("total_votos_nulos_parlamentarios_mercosur").toString()));
      	 m.setTotalVotosNulosSenadores(Integer.valueOf(dataRequest.get("total_votos_nulos_senadores").toString()));
      	 
    
      	 m.setTotalVotosRecurridosDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_recurridos_diputados_nacionales").toString()));
      	 m.setTotalVotosRecurridosLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_recurridos_legisladores_provinciales").toString()));
      	 m.setTotalVotosRecurridosConcejales(Integer.valueOf(dataRequest.get("total_votos_recurridos_concejales").toString()));
      	 m.setTotalVotosRecurridosPresidente(Integer.valueOf(dataRequest.get("total_votos_recurridos_presidente").toString()));
      	 m.setTotalVotosRecurridosGobernador(Integer.valueOf(dataRequest.get("total_votos_recurridos_gobernador").toString()));
      	 m.setTotalVotosRecurridosParlamentariosMercosur(Integer.valueOf(dataRequest.get("total_votos_recurridos_parlamentarios_mercosur").toString()));
      	 m.setTotalVotosRecurridosIntendente(Integer.valueOf(dataRequest.get("total_votos_recurridos_intendente").toString()));
      	 m.setTotalVotosRecurridosSenadores(Integer.valueOf(dataRequest.get("total_votos_recurridos_senadores").toString()));
      	 
      	 
      	 

       	 m.setTotalVotosImpugnadoDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_impugnados_diputados_nacionales").toString()));
      	 m.setTotalVotosImpugnadoLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_impugnados_legisladores_provinciales").toString()));
      	 m.setTotalVotosImpugnadoConcejales(Integer.valueOf(dataRequest.get("total_votos_impugnados_concejales").toString()));
      	 m.setTotalVotosImpugnadoPresidente(Integer.valueOf(dataRequest.get("total_votos_impugnados_presidente").toString()));
      	 m.setTotalVotosImpugnadoGobernador(Integer.valueOf(dataRequest.get("total_votos_impugnados_presidente").toString()));
      	 m.setTotalVotosImpugnadoParlamentariosMercosur(Integer.valueOf(dataRequest.get("total_votos_impugnados_parlamentarios_mercosur").toString()));
      	 m.setTotalVotosImpugnadoIntendente(Integer.valueOf(dataRequest.get("total_votos_impugnados_intendente").toString()));
      	 m.setTotalVotosImpugnadoSenadores(Integer.valueOf(dataRequest.get("total_votos_impugnados_senadores").toString()));
      	 

      	 m.setTotalVotosBolsinDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_diputados_nacionales").toString()));
      	 m.setTotalVotosBolsinLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_legisladores_provinciales").toString()));
      	 m.setTotalVotosBolsinConcejales(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_concejales").toString()));
      	 m.setTotalVotosBolsinPresidente(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_presidente").toString()));
      	 m.setTotalVotosBolsinGobernador(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_gobernador").toString()));
      	 m.setTotalVotosBolsinParlamentariosMercosur(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_parlamentarios_mercosur").toString()));
      	 m.setTotalVotosBolsinSenadores(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_senadores").toString()));
      	 m.setTotalVotosBolsinIntendente(Integer.valueOf(dataRequest.get("total_votos_comando_electoral_intendente").toString()));

      	 m.setTotalVotosBlancoDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_en_blanco_diputados_nacionales").toString()));
      	 m.setTotalVotosBlancoLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_en_blanco_legisladores_provinciales").toString()));
      	 m.setTotalVotosBlancoConcejales(Integer.valueOf(dataRequest.get("total_votos_en_blanco_concejales").toString()));
      	 m.setTotalVotosBlancoPresidente(Integer.valueOf(dataRequest.get("total_votos_en_blanco_presidente").toString()));
      	 m.setTotalVotosBlancoGobernador(Integer.valueOf(dataRequest.get("total_votos_en_blanco_gobernador").toString()));
      	 m.setTotalVotosBlancoParlamentariosMercosur(Integer.valueOf(dataRequest.get("total_votos_en_blanco_parlamentarios_mercosur").toString()));
      	 m.setTotalVotosBlancoSenadores(Integer.valueOf(dataRequest.get("total_votos_en_blanco_senadores").toString()));
      	 m.setTotalVotosBlancoIntendente(Integer.valueOf(dataRequest.get("total_votos_en_blanco_senadores").toString()));

      	 m.setTotalXColumnaDiputadosNacionales(Integer.valueOf(dataRequest.get("total_votos_x_columna_diputados_nacionales").toString()));
      	 m.setTotalXColumnaLegisladoresProvinciales(Integer.valueOf(dataRequest.get("total_votos_x_columna_legisladores_provinciales").toString()));
      	 m.setTotalVotosXColumnaConcejales(Integer.valueOf(dataRequest.get("total_votos_x_columna_concejales").toString()));
      	 m.setTotalVotosXColumnaPresidente(Integer.valueOf(dataRequest.get("total_votos_x_columna_presidente").toString()));
      	 m.setTotalVotosXColumnaGobernador(Integer.valueOf(dataRequest.get("total_votos_x_columna_gobernador").toString()));
      	 m.setTotalVotosXColumnaParlamentariosMercosur(Integer.valueOf(dataRequest.get("total_votos_x_columna_parlamentarios_mercosur").toString()));
      	 m.setTotalVotosXColumnaSenadores(Integer.valueOf(dataRequest.get("total_votos_x_columna_senadores").toString()));
      	 m.setTotalVotosXColumnaIntendente(Integer.valueOf(dataRequest.get("total_votos_x_columna_intendente").toString()));


      	 /*Generar los escrutinios para esta mesa*/


      	 @SuppressWarnings("unchecked")
			 List<HashMap<String,Object>> escrutinios_param = (List<HashMap<String,Object>>)dataRequest.get("escrutinio_detalle");

      	 Iterator<HashMap<String,Object>> it_escrutinios = escrutinios_param.iterator();

      	 Set<Escrutinio> escrutinios = new HashSet<Escrutinio>();

      	 while(it_escrutinios.hasNext())
      	 {
      		 HashMap<String,Object> hm = it_escrutinios.next();

      		 Escrutinio esc = new Escrutinio();

      		 esc.setIdListaInterna(Integer.valueOf(hm.get("lista_interna").toString()));


      		 esc.setTotalVotosDiputadosNacionales(Integer.valueOf(hm.get("diputados_nacionales").toString()));

      		 esc.setTotalVotosLegisladoresProvinciales(Integer.valueOf(hm.get("legisladores_provinciales").toString()));

      		 esc.setTotalVotosConcejales(Integer.valueOf(hm.get("concejales").toString()));
      		 
      		 esc.setTotalVotosIntendente(Integer.valueOf(hm.get("intendente").toString()));
      		 
      		 esc.setTotalVotosPresidente(Integer.valueOf(hm.get("presidente").toString()));
      		 
      		 esc.setTotalVotosGobernador(Integer.valueOf(hm.get("gobernador").toString()));
      		 
      		 esc.setTotalVotosParlamentariosMercosur(Integer.valueOf(hm.get("parlamentarios_mercosur").toString()));
      		 
      		 esc.setTotalVotosSenadores(Integer.valueOf(hm.get("senadores").toString()));

      		 escrutinios.add(esc);

      	 }
      	 m.getEscrutinios().clear();

      	 m.getEscrutinios().addAll(escrutinios);


      	 mesaRepository.saveAndFlush(m);
      }
      else
      	returnValue.put("existe_mesa", false);
      
		
		
        return returnValue;
	}

	@RequestMapping("/descargar")
    public void downloadPDFResource( HttpServletRequest request,
                                     HttpServletResponse response)
    {
        //If user is not authorized - he should be thrown out from here itself

        //Authorized user will download the file
        //String dataDirectory = "C:\\Users\\sergio\\Documents\\";
		//String dataDirectory = "/tmp";
        String relativeDirectoryPath = "data/"; 
        String filePath = System.getProperty("user.dir") + "/" + relativeDirectoryPath;
        Path file = Paths.get(filePath, "Paso2023.xlsx");
        if (Files.exists(file))
        {
            response.setContentType("application/excel");
            response.addHeader("Content-Disposition", "attachment; filename=DetalleGenerales2021.xlsx");
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	
	

    @RequestMapping(value={"/generateReport"}, method={RequestMethod.GET}, produces={"application/json"})
    @ResponseBody
    public Map<String,Object> generateReport() throws IOException {
    	
    	/*Crear encabezado de columnas*/
    	
    	 XSSFWorkbook workbook = new XSSFWorkbook();
         XSSFSheet sheet = workbook.createSheet("Detalle de Mesas Cargadas");
         

         XSSFCellStyle createCellStyle = workbook.createCellStyle();
         createCellStyle.setFillBackgroundColor(new XSSFColor(Color.BLACK));
         XSSFFont createFont = workbook.createFont();
         createCellStyle.setFont(createFont);
         createFont.setBold(true);
         
         
          
         Object[][] bookData = {
                 {"Circuito", "Mesa", "Direccion", "Nombre", "Cargo", "Cant Sobres", "Cant Votantes","ALIANZA + VALORES","PARTIDO UNION CELESTE Y BLANCO","AZA.AVANZA LIBERTAD","FTE.DE IZQ.Y DE TRABAJADORES-UNIDAD","AZA.JUNTOS","FRENTE DE TODOS","FRENTE VAMOS CON VOS","VOTOS NULOS","VOTOS RECURRIDOS","VOTOS IMPUGNADOS","VOTOS DEL COMANDO ELECTORAL","VOTOS EN BLANCO"}
         };
         
         
         
         
         
  
         int rowCount = 0;
          
         for (Object[] aBook : bookData) {
             Row row = sheet.createRow(rowCount++);
              
             int columnCount = 0;
              
             for (Object field : aBook) {
                 Cell cell = row.createCell(columnCount++);
                 if (field instanceof String) {
                     cell.setCellValue((String) field);
                 } else if (field instanceof Integer) {
                     cell.setCellValue((Integer) field);
                 }
                 
                 cell.setCellStyle(createCellStyle);
             }
              
         }
    	
    	HashMap<String, Object> returnValue = new HashMap<String,Object>();	
    	
    	
        //plasmar la parte de presidente

    	
    	List<Mesa> mesasAll_presidente = mesaRepository.findLoaded();
    	Iterator<Mesa> it_mesas_presidente = mesasAll_presidente.iterator();
    	while(it_mesas_presidente.hasNext()) {
    		
    		Mesa mesa = it_mesas_presidente.next();
    		
    		List<Escrutinio> escrutinios = new ArrayList<Escrutinio>(mesa.getEscrutinios());
    		
    		Collections.sort(escrutinios, new Comparator<Escrutinio>(){
    			@Override
    			public int compare(Escrutinio e2, Escrutinio e1) {
    				return Integer.valueOf(e2.getIdListaInterna()).compareTo(e1.getIdListaInterna());
    			}
    		});
    		
    		
    		//si la lista no está vacía
    		
    		if(escrutinios.size()>0) {
    			
    			//crea fila
    			Row row = sheet.createRow(rowCount++);
    			
    			int columnCount = 0;
    			CircuitoMesa cm = circuitoMesaRepository.findCircuitoMesa(mesa.getCircuito(), mesa.getMesa());
    			
    			Cell cell1 = row.createCell(columnCount++);
    			cell1.setCellValue(cm.getCircuito());
    			
    			Cell cell2 = row.createCell(columnCount++);
    			cell2.setCellValue(mesa.getMesa());
    			
    			Cell cell3 = row.createCell(columnCount++);
    			cell3.setCellValue(cm.getDireccion());
    			
    			Cell cell4 = row.createCell(columnCount++);
    			cell4.setCellValue(cm.getNombre());
    			
    			Cell cell5 = row.createCell(columnCount++);
    			cell5.setCellValue("PRESIDENTE");
    			
    			Cell cell6 = row.createCell(columnCount++);
    			cell6.setCellValue(mesa.getTotalsobres());
    			
    			Cell cell7 = row.createCell(columnCount++);
    			cell7.setCellValue(mesa.getTotalVotosXIndividuo());
    			
    			Iterator<Escrutinio> it_escrutinios = escrutinios.iterator();
    			
    			while(it_escrutinios.hasNext()) {
    				Escrutinio e = it_escrutinios.next();
    				Cell cellEscrutinio = row.createCell(columnCount++);
    				cellEscrutinio.setCellValue(e.getTotalVotosPresidente());
    			}
    			
    			Cell cell8 = row.createCell(columnCount++);
    			cell8.setCellValue(mesa.getTotalVotosNulosPresidente());
    			
    			Cell cell9 = row.createCell(columnCount++);
    			cell9.setCellValue(mesa.getTotalVotosRecurridosPresidente());
    			
    			Cell cell10 = row.createCell(columnCount++);
    			cell10.setCellValue(mesa.getTotalVotosImpugnadoPresidente());
    			
    			Cell cell11 = row.createCell(columnCount++);
    			cell11.setCellValue(mesa.getTotalVotosBolsinPresidente());
    			
    			Cell cell12 = row.createCell(columnCount++);
    			cell12.setCellValue(mesa.getTotalVotosBlancoPresidente());
    			
    		}
    		    		
    	}
    	
    	
        //ahora plasmar la parte de senadores
    	
    	List<Mesa> mesasAll_senadores = mesaRepository.findLoaded();
    	Iterator<Mesa> it_mesas_senadores = mesasAll_senadores.iterator();
    	while(it_mesas_senadores.hasNext()) {
    		
    		Mesa mesa = it_mesas_senadores.next();
    		
    		List<Escrutinio> escrutinios = new ArrayList<Escrutinio>(mesa.getEscrutinios());
    		
    		Collections.sort(escrutinios, new Comparator<Escrutinio>(){
    			@Override
    			public int compare(Escrutinio e2, Escrutinio e1) {
    				return Integer.valueOf(e2.getIdListaInterna()).compareTo(e1.getIdListaInterna());
    			}
    		});
    		
    		
    		//si la lista no está vacía
    		
    		if(escrutinios.size()>0) {
    			
    			//crea fila
    			Row row = sheet.createRow(rowCount++);
    			
    			int columnCount = 0;
    			CircuitoMesa cm = circuitoMesaRepository.findCircuitoMesa(mesa.getCircuito(), mesa.getMesa());
    			
    			Cell cell1 = row.createCell(columnCount++);
    			cell1.setCellValue(cm.getCircuito());
    			
    			Cell cell2 = row.createCell(columnCount++);
    			cell2.setCellValue(mesa.getMesa());
    			
    			Cell cell3 = row.createCell(columnCount++);
    			cell3.setCellValue(cm.getDireccion());
    			
    			Cell cell4 = row.createCell(columnCount++);
    			cell4.setCellValue(cm.getNombre());
    			
    			Cell cell5 = row.createCell(columnCount++);
    			cell5.setCellValue("SENADORES");
    			
    			Cell cell6 = row.createCell(columnCount++);
    			cell6.setCellValue(mesa.getTotalsobres());
    			
    			Cell cell7 = row.createCell(columnCount++);
    			cell7.setCellValue(mesa.getTotalVotosXIndividuo());
    			
    			Iterator<Escrutinio> it_escrutinios = escrutinios.iterator();
    			
    			while(it_escrutinios.hasNext()) {
    				Escrutinio e = it_escrutinios.next();
    				Cell cellEscrutinio = row.createCell(columnCount++);
    				cellEscrutinio.setCellValue(e.getTotalVotosSenadores());
    			}
    			
    			Cell cell8 = row.createCell(columnCount++);
    			cell8.setCellValue(mesa.getTotalVotosNulosSenadores());
    			
    			Cell cell9 = row.createCell(columnCount++);
    			cell9.setCellValue(mesa.getTotalVotosRecurridosSenadores());
    			
    			Cell cell10 = row.createCell(columnCount++);
    			cell10.setCellValue(mesa.getTotalVotosImpugnadoSenadores());
    			
    			Cell cell11 = row.createCell(columnCount++);
    			cell11.setCellValue(mesa.getTotalVotosBolsinSenadores());
    			
    			Cell cell12 = row.createCell(columnCount++);
    			cell12.setCellValue(mesa.getTotalVotosBlancoSenadores());
    			
    		}
    		    		
    	}
    	
    	
    	//Plasmar la parte de los Diputados Nacionales
    	
    	List<Mesa> mesasAll_diputados = mesaRepository.findLoaded();
    	
    	Iterator<Mesa> it_mesas_diputados = mesasAll_diputados.iterator();
    	
    	while(it_mesas_diputados.hasNext())
    	{
    		Mesa m = it_mesas_diputados.next();
    		
    		
    		
    		List<Escrutinio> escrutinios = new ArrayList<Escrutinio>(m.getEscrutinios()); 
    		
    		
    		// Sorting
    		Collections.sort(escrutinios, new Comparator<Escrutinio>() {
    		        @Override
    		        public int compare(Escrutinio e2, Escrutinio e1)
    		        {
    		            return Integer.valueOf(e2.getIdListaInterna()).compareTo(e1.getIdListaInterna());
    		        }
    		    });
    		
    		
    		/*Solo si la lista no esta vacia*/
    		
    		if(escrutinios.size()>0)
    		{
    			//Crear una fila para esta mesa
    			Row row = sheet.createRow(rowCount++);
    			
    			int columnCount = 0;
    			
    			//obtener el lugar de votacion
    			
    			CircuitoMesa cm = circuitoMesaRepository.findCircuitoMesa(m.getCircuito(), m.getMesa());

    			
    			Cell cell1 = row.createCell(columnCount++);
    			
    			cell1.setCellValue(cm.getCircuito());
    			
    			Cell cell2 = row.createCell(columnCount++);
    			
    			cell2.setCellValue(m.getMesa());
    			
    			
    			Cell cell3 = row.createCell(columnCount++);
    			
    			cell3.setCellValue(cm.getDireccion());
    			
    			Cell cell4 = row.createCell(columnCount++);
    			
    			cell4.setCellValue(cm.getNombre());
    			
    			Cell cell5 = row.createCell(columnCount++);
    			
    			cell5.setCellValue("DIPUTADOS NACIONALES");

    			Cell cell6 = row.createCell(columnCount++);
    			
    			cell6.setCellValue(m.getTotalsobres());
    			
    			Cell cell7 = row.createCell(columnCount++);
    			
    			cell7.setCellValue(m.getTotalVotosXIndividuo());

    			
    			Iterator<Escrutinio> it_escrutinios = escrutinios.iterator();
    			
    			
    			while(it_escrutinios.hasNext())
    			{
    				Escrutinio e = it_escrutinios.next();
    				Cell cellEsc = row.createCell(columnCount++);
    				cellEsc.setCellValue(e.getTotalVotosDiputadosNacionales());
    			}
    			
    			Cell cell8 = row.createCell(columnCount++);
    			
    			cell8.setCellValue(m.getTotalVotosNulosDiputadosNacionales());
    			
    			Cell cell9 = row.createCell(columnCount++);
    			
    			cell9.setCellValue(m.getTotalVotosRecurridosDiputadosNacionales());
    			
    			Cell cell10 = row.createCell(columnCount++);
    			
    			cell10.setCellValue(m.getTotalVotosImpugnadoDiputadosNacionales());
    			
    			Cell cell11 = row.createCell(columnCount++);
    			
    			cell11.setCellValue(m.getTotalVotosBolsinDiputadosNacionales());
    			
    			Cell cell12 = row.createCell(columnCount++);
    			
    			cell12.setCellValue(m.getTotalVotosBlancoDiputadosNacionales());
    			
    		}
    		
    	}

    	
    	//PARLAMENTARIOS DEL MERCOSUR
    	
    	List<Mesa> mesasAll_parlamentarios_mercosur = mesaRepository.findLoaded();
    	
    	Iterator<Mesa> it_mesas_parlamentarios_mercosur = mesasAll_parlamentarios_mercosur.iterator();
    	
    	while(it_mesas_parlamentarios_mercosur.hasNext())
    	{
    		Mesa m = it_mesas_parlamentarios_mercosur.next();
    		List<Escrutinio> escrutinios = new ArrayList<Escrutinio>(m.getEscrutinios()); 
    		
    		
    		// Sorting
    		Collections.sort(escrutinios, new Comparator<Escrutinio>() {
    		        @Override
    		        public int compare(Escrutinio e2, Escrutinio e1)
    		        {
    		            return Integer.valueOf(e2.getIdListaInterna()).compareTo(e1.getIdListaInterna());
    		        }
    		    });
    		
    		
    		/*Solo si la lista no esta vacia*/
    		
    		if(escrutinios.size()>0)
    		{
    			//Crear una fila para esta mesa
    			Row row = sheet.createRow(rowCount++);
    			
    			int columnCount = 0;
    			
    			//obtener el lugar de votacion
    			
    			CircuitoMesa cm = circuitoMesaRepository.findCircuitoMesa(m.getCircuito(), m.getMesa());
    			
    			
    			Cell cell1 = row.createCell(columnCount++);
    			
    			cell1.setCellValue(cm.getCircuito());
    			
    			Cell cell2 = row.createCell(columnCount++);
    			
    			cell2.setCellValue(m.getMesa());
    			
    			
    			Cell cell3 = row.createCell(columnCount++);
    			
    			cell3.setCellValue(cm.getDireccion());
    			
    			Cell cell4 = row.createCell(columnCount++);
    			
    			cell4.setCellValue(cm.getNombre());
    			
    			Cell cell5 = row.createCell(columnCount++);
    			
    			cell5.setCellValue("PARLAMENTARIOS DEL MERCOSUR");

    			Cell cell6 = row.createCell(columnCount++);
    			
    			cell6.setCellValue(m.getTotalsobres());
    			
    			Cell cell7 = row.createCell(columnCount++);
    			
    			cell7.setCellValue(m.getTotalVotosXIndividuo());

    			
    			Iterator<Escrutinio> it_escrutinios = escrutinios.iterator();
    			
    			
    			while(it_escrutinios.hasNext())
    			{
    				Escrutinio e = it_escrutinios.next();
    				Cell cellEsc = row.createCell(columnCount++);
    				cellEsc.setCellValue(e.getTotalVotosParlamentariosMercosur());
    			}
    			
    			Cell cell8 = row.createCell(columnCount++);
    			
    			cell8.setCellValue(m.getTotalVotosNulosParlamentariosMercosur());
    			
    			Cell cell9 = row.createCell(columnCount++);
    			
    			cell9.setCellValue(m.getTotalVotosRecurridosParlamentariosMercosur());
    			
    			Cell cell10 = row.createCell(columnCount++);
    			
    			cell10.setCellValue(m.getTotalVotosImpugnadoParlamentariosMercosur());
    			
    			Cell cell11 = row.createCell(columnCount++);
    			
    			cell11.setCellValue(m.getTotalVotosBolsinParlamentariosMercosur());
    			
    			Cell cell12 = row.createCell(columnCount++);
    			
    			cell12.setCellValue(m.getTotalVotosBlancoParlamentariosMercosur());
    			
    		}
    		
    	}

    	
    	//GOBERNADOR
    	List<Mesa> mesasAll_gobernador = mesaRepository.findLoaded();
    	
    	Iterator<Mesa> it_mesas_gobernador = mesasAll_gobernador.iterator();
    	
    	while(it_mesas_gobernador.hasNext())
    	{
    		Mesa m = it_mesas_gobernador.next();
    		List<Escrutinio> escrutinios = new ArrayList<Escrutinio>(m.getEscrutinios()); 
    		
    		
    		// Sorting
    		Collections.sort(escrutinios, new Comparator<Escrutinio>() {
    		        @Override
    		        public int compare(Escrutinio e2, Escrutinio e1)
    		        {
    		            return Integer.valueOf(e2.getIdListaInterna()).compareTo(e1.getIdListaInterna());
    		        }
    		    });
    		
    		
    		/*Solo si la lista no esta vacia*/
    		
    		if(escrutinios.size()>0)
    		{
    			//Crear una fila para esta mesa
    			Row row = sheet.createRow(rowCount++);
    			
    			int columnCount = 0;
    			
    			//obtener el lugar de votacion
    			
    			CircuitoMesa cm = circuitoMesaRepository.findCircuitoMesa(m.getCircuito(), m.getMesa());
    			
    			
    			Cell cell1 = row.createCell(columnCount++);
    			
    			cell1.setCellValue(cm.getCircuito());
    			
    			Cell cell2 = row.createCell(columnCount++);
    			
    			cell2.setCellValue(m.getMesa());
    			
    			
    			Cell cell3 = row.createCell(columnCount++);
    			
    			cell3.setCellValue(cm.getDireccion());
    			
    			Cell cell4 = row.createCell(columnCount++);
    			
    			cell4.setCellValue(cm.getNombre());
    			
    			Cell cell5 = row.createCell(columnCount++);
    			
    			cell5.setCellValue("GOBERNADOR");

    			Cell cell6 = row.createCell(columnCount++);
    			
    			cell6.setCellValue(m.getTotalsobres());
    			
    			Cell cell7 = row.createCell(columnCount++);
    			
    			cell7.setCellValue(m.getTotalVotosXIndividuo());

    			
    			Iterator<Escrutinio> it_escrutinios = escrutinios.iterator();
    			
    			
    			while(it_escrutinios.hasNext())
    			{
    				Escrutinio e = it_escrutinios.next();
    				Cell cellEsc = row.createCell(columnCount++);
    				cellEsc.setCellValue(e.getTotalVotosParlamentariosMercosur());
    			}
    			
    			Cell cell8 = row.createCell(columnCount++);
    			
    			cell8.setCellValue(m.getTotalVotosNulosGobernador());
    			
    			Cell cell9 = row.createCell(columnCount++);
    			
    			cell9.setCellValue(m.getTotalVotosRecurridosGobernador());
    			
    			Cell cell10 = row.createCell(columnCount++);
    			
    			cell10.setCellValue(m.getTotalVotosImpugnadoGobernador());
    			
    			Cell cell11 = row.createCell(columnCount++);
    			
    			cell11.setCellValue(m.getTotalVotosBolsinGobernador());
    			
    			Cell cell12 = row.createCell(columnCount++);
    			
    			cell12.setCellValue(m.getTotalVotosBlancoGobernador());
    			
    		}
    		
    	}
    	
    	
    	//Plasmar la parte de los Legisladores Provinciales
    	System.out.println("legisladores");
    	List<Mesa> mesasAll_legisladores_provinciales = mesaRepository.findLoaded();
    	
    	Iterator<Mesa> it_mesas_legisladores_provinciales = mesasAll_legisladores_provinciales.iterator();
    	
    	while(it_mesas_legisladores_provinciales.hasNext())
    	{
    		Mesa m = it_mesas_legisladores_provinciales.next();
    		List<Escrutinio> escrutinios = new ArrayList<Escrutinio>(m.getEscrutinios()); 
    		
    		
    		// Sorting
    		Collections.sort(escrutinios, new Comparator<Escrutinio>() {
    		        @Override
    		        public int compare(Escrutinio e2, Escrutinio e1)
    		        {
    		            return Integer.valueOf(e2.getIdListaInterna()).compareTo(e1.getIdListaInterna());
    		        }
    		    });
    		
    		
    		/*Solo si la lista no esta vacia*/
    		
    		if(escrutinios.size()>0)
    		{
    			//Crear una fila para esta mesa
    			Row row = sheet.createRow(rowCount++);
    			
    			int columnCount = 0;
    			
    			//obtener el lugar de votacion
    			
    			CircuitoMesa cm = circuitoMesaRepository.findCircuitoMesa(m.getCircuito(), m.getMesa());
    			
    			
    			Cell cell1 = row.createCell(columnCount++);
    			
    			cell1.setCellValue(cm.getCircuito());
    			
    			Cell cell2 = row.createCell(columnCount++);
    			
    			cell2.setCellValue(m.getMesa());
    			
    			
    			Cell cell3 = row.createCell(columnCount++);
    			
    			cell3.setCellValue(cm.getDireccion());
    			
    			Cell cell4 = row.createCell(columnCount++);
    			
    			cell4.setCellValue(cm.getNombre());
    			
    			Cell cell5 = row.createCell(columnCount++);
    			
    			cell5.setCellValue("LEGISLADORES PROVINCIALES");

    			Cell cell6 = row.createCell(columnCount++);
    			
    			cell6.setCellValue(m.getTotalsobres());
    			
    			Cell cell7 = row.createCell(columnCount++);
    			
    			cell7.setCellValue(m.getTotalVotosXIndividuo());

    			
    			Iterator<Escrutinio> it_escrutinios = escrutinios.iterator();
    			
    			
    			while(it_escrutinios.hasNext())
    			{
    				Escrutinio e = it_escrutinios.next();
    				Cell cellEsc = row.createCell(columnCount++);
    				cellEsc.setCellValue(e.getTotalVotosLegisladoresProvinciales());
    			}
    			
    			Cell cell8 = row.createCell(columnCount++);
    			
    			cell8.setCellValue(m.getTotalVotosNulosLegisladoresProvinciales());
    			
    			Cell cell9 = row.createCell(columnCount++);
    			
    			cell9.setCellValue(m.getTotalVotosRecurridosLegisladoresProvinciales());
    			
    			Cell cell10 = row.createCell(columnCount++);
    			
    			cell10.setCellValue(m.getTotalVotosImpugnadoLegisladoresProvinciales());
    			
    			Cell cell11 = row.createCell(columnCount++);
    			
    			cell11.setCellValue(m.getTotalVotosBolsinLegisladoresProvinciales());
    			
    			Cell cell12 = row.createCell(columnCount++);
    			
    			cell12.setCellValue(m.getTotalVotosBlancoLegisladoresProvinciales());
    			
    		}
    		
    	}
    	
    	
    	
    	
    	//PARLAMENTARIOS DEL MERCOSUR
    	List<Mesa> mesasAll_intendente = mesaRepository.findLoaded();
    	
    	Iterator<Mesa> it_mesas_intendente = mesasAll_intendente.iterator();
    	
    	while(it_mesas_intendente.hasNext())
    	{
    		Mesa m = it_mesas_intendente.next();
    		List<Escrutinio> escrutinios = new ArrayList<Escrutinio>(m.getEscrutinios()); 
    		
    		
    		// Sorting
    		Collections.sort(escrutinios, new Comparator<Escrutinio>() {
    		        @Override
    		        public int compare(Escrutinio e2, Escrutinio e1)
    		        {
    		            return Integer.valueOf(e2.getIdListaInterna()).compareTo(e1.getIdListaInterna());
    		        }
    		    });
    		
    		
    		/*Solo si la lista no esta vacia*/
    		
    		if(escrutinios.size()>0)
    		{
    			//Crear una fila para esta mesa
    			Row row = sheet.createRow(rowCount++);
    			
    			int columnCount = 0;
    			
    			//obtener el lugar de votacion
    			
    			CircuitoMesa cm = circuitoMesaRepository.findCircuitoMesa(m.getCircuito(), m.getMesa());
    			
    			
    			Cell cell1 = row.createCell(columnCount++);
    			
    			cell1.setCellValue(cm.getCircuito());
    			
    			Cell cell2 = row.createCell(columnCount++);
    			
    			cell2.setCellValue(m.getMesa());
    			
    			
    			Cell cell3 = row.createCell(columnCount++);
    			
    			cell3.setCellValue(cm.getDireccion());
    			
    			Cell cell4 = row.createCell(columnCount++);
    			
    			cell4.setCellValue(cm.getNombre());
    			
    			Cell cell5 = row.createCell(columnCount++);
    			
    			cell5.setCellValue("INTENDENTE");

    			Cell cell6 = row.createCell(columnCount++);
    			
    			cell6.setCellValue(m.getTotalsobres());
    			
    			Cell cell7 = row.createCell(columnCount++);
    			
    			cell7.setCellValue(m.getTotalVotosXIndividuo());

    			
    			Iterator<Escrutinio> it_escrutinios = escrutinios.iterator();
    			
    			
    			while(it_escrutinios.hasNext())
    			{
    				Escrutinio e = it_escrutinios.next();
    				Cell cellEsc = row.createCell(columnCount++);
    				cellEsc.setCellValue(e.getTotalVotosIntendente());
    			}
    			
    			Cell cell8 = row.createCell(columnCount++);
    			
    			cell8.setCellValue(m.getTotalVotosNulosIntendente());
    			
    			Cell cell9 = row.createCell(columnCount++);
    			
    			cell9.setCellValue(m.getTotalVotosRecurridosIntendente());
    			
    			Cell cell10 = row.createCell(columnCount++);
    			
    			cell10.setCellValue(m.getTotalVotosImpugnadoIntendente());
    			
    			Cell cell11 = row.createCell(columnCount++);
    			
    			cell11.setCellValue(m.getTotalVotosBolsinIntendente());
    			
    			Cell cell12 = row.createCell(columnCount++);
    			
    			cell12.setCellValue(m.getTotalVotosBlancoIntendente());
    			
    		}
    		
    	}
    	
    	
    	
    	
    	//Plasmar la parte de Concejales
    	
    	List<Mesa> mesasAll_concejales = mesaRepository.findLoaded();
    	
    	Iterator<Mesa> it_mesas_concejales = mesasAll_concejales.iterator();
    	
    	while(it_mesas_concejales.hasNext())
    	{
    		Mesa m = it_mesas_concejales.next();
    		List<Escrutinio> escrutinios = new ArrayList<Escrutinio>(m.getEscrutinios()); 
    		
    		
    		// Sorting
    		Collections.sort(escrutinios, new Comparator<Escrutinio>() {
    		        @Override
    		        public int compare(Escrutinio e2, Escrutinio e1)
    		        {
    		            return Integer.valueOf(e2.getIdListaInterna()).compareTo(e1.getIdListaInterna());
    		        }
    		    });
    		
    		
    		/*Solo si la lista no esta vacia*/
    		
    		if(escrutinios.size()>0)
    		{
    			//Crear una fila para esta mesa
    			Row row = sheet.createRow(rowCount++);
    			
    			int columnCount = 0;
    			
    			//obtener el lugar de votacion
    			
    			CircuitoMesa cm = circuitoMesaRepository.findCircuitoMesa(m.getCircuito(), m.getMesa());
    			
    			
    			Cell cell1 = row.createCell(columnCount++);
    			
    			cell1.setCellValue(cm.getCircuito());
    			
    			Cell cell2 = row.createCell(columnCount++);
    			
    			cell2.setCellValue(m.getMesa());
    			
    			
    			Cell cell3 = row.createCell(columnCount++);
    			
    			cell3.setCellValue(cm.getDireccion());
    			
    			Cell cell4 = row.createCell(columnCount++);
    			
    			cell4.setCellValue(cm.getNombre());
    			
    			Cell cell5 = row.createCell(columnCount++);
    			
    			cell5.setCellValue("CONCEJALES");

    			Cell cell6 = row.createCell(columnCount++);
    			
    			cell6.setCellValue(m.getTotalsobres());
    			
    			Cell cell7 = row.createCell(columnCount++);
    			
    			cell7.setCellValue(m.getTotalVotosXIndividuo());

    			
    			Iterator<Escrutinio> it_escrutinios = escrutinios.iterator();
    			
    			
    			while(it_escrutinios.hasNext())
    			{
    				Escrutinio e = it_escrutinios.next();
    				Cell cellEsc = row.createCell(columnCount++);
    				cellEsc.setCellValue(e.getTotalVotosConcejales());
    			}
    			
    			Cell cell8 = row.createCell(columnCount++);
    			
    			cell8.setCellValue(m.getTotalVotosNulosConcejales());
    			
    			Cell cell9 = row.createCell(columnCount++);
    			
    			cell9.setCellValue(m.getTotalVotosRecurridosConcejales());
    			
    			Cell cell10 = row.createCell(columnCount++);
    			
    			cell10.setCellValue(m.getTotalVotosImpugnadoConcejales());
    			
    			Cell cell11 = row.createCell(columnCount++);
    			
    			cell11.setCellValue(m.getTotalVotosBolsinConcejales());
    			
    			Cell cell12 = row.createCell(columnCount++);
    			
    			cell12.setCellValue(m.getTotalVotosBlancoConcejales());
    			
    		}
    		
    	}
    	

    	
    	
    	
    	
    	
    	for(int i=0;i<bookData[0].length;i++)
    		
    		sheet.autoSizeColumn(i);
    	

    	String relativeDirectoryPath = "data/"; 

    	// Combina la ruta del directorio relativo con el nombre del archivo
    	String filePath = System.getProperty("user.dir") + "/" + relativeDirectoryPath + "Paso2023.xlsx";
    	// Asegurarse de que el directorio exista, si no, crearlo
    	try {
    	    Files.createDirectories(Paths.get(System.getProperty("user.dir"), relativeDirectoryPath));
    	} catch (IOException e) {
    	    e.printStackTrace();
    	    returnValue.put("success", false);
    	    returnValue.put("message", "Ha ocurrido un error al crear el directorio");
    	    return returnValue;
    	}
    	//Escupir el archivo a la direccion utilizada
       // try (FileOutputStream outputStream = new FileOutputStream("C:\\Users\\sergio\\Documents\\Paso2023.xlsx")) {
    	//try (FileOutputStream outputStream = new FileOutputStream("/tmp/DetallePaso2019.xlsx")) {
    	try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
    		workbook.write(outputStream);
            returnValue.put("success", true);
        }
        catch (Exception e) {
        	e.printStackTrace();
        	returnValue.put("success", false);
        	returnValue.put("message", "Ha ocurrido un error al generar el archivo Excel");
        	
		}
    	return returnValue;

    }

	
	
	
}
