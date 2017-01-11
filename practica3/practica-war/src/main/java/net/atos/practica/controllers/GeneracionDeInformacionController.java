package net.atos.practica.controllers;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import net.atos.practica.dto.FiltroGenerarInformacion;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Promocion;
import net.atos.practica.entity.Proyecto;
import net.atos.practica.negocio.CapacidadBO;
import net.atos.practica.negocio.GeneracionInformacionBO;
import net.atos.practica.negocio.PromocionBO;
import net.atos.practica.negocio.ProyectoBO;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class GeneracionDeInformacionController{
	@Autowired
	private GeneracionInformacionBO generacionInformacionBO;

	@Autowired
	CapacidadBO capacidadBo;

	@Autowired
	PromocionBO promocionBo;

	@Autowired
	ProyectoBO proyectoBo;
	static Logger log = Logger.getLogger(LoginController.class);

	private FiltroGenerarInformacion filtroBusqueda;
	private List<Colaborador> listaResultado;
	private List<String> nivelesGCM;
	private List<Capacidad> capacidades;
	private List<Promocion> promociones;
	private List<Proyecto> proyectos;
	private String gcmCrud;
	private int aux;
	private List<Boolean> listaVisible;

	private static final int MAX = 10;
	private static final int MIN = -1;

	@PostConstruct
	public void init() {
		filtroBusqueda = new FiltroGenerarInformacion();
		nivelesGCM = new ArrayList<String>();
		capacidades = capacidadBo.listarCapacidades();
		promociones = promocionBo.listarPromociones();
		proyectos = proyectoBo.listarProyectos();
		listaVisible = Arrays.asList(false, true, true, true, true, false,
				false, false, false, false, true, false, false, true, false,
				false, true, false, true, true);
		for (Integer i = MIN; i < MAX; i++) {
			if (i == MIN) {
				nivelesGCM.add("Todos");
			} else {
				nivelesGCM.add(i.toString());
			}
		}
	}

	public void buscar() {
		if (gcmCrud.equals("Todos")) {
			filtroBusqueda.setGcm(MIN);
		} else {
			aux = Integer.parseInt(gcmCrud);
			filtroBusqueda.setGcm(aux);
		}
		listaResultado = new ArrayList<Colaborador>();
		listaResultado = generacionInformacionBO.buscar(filtroBusqueda);
	}

	public void onToggle(ToggleEvent e) {
		listaVisible.set((Integer) e.getData(),
				e.getVisibility() == Visibility.VISIBLE);
	}

	public void preProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		sheet.setDefaultRowHeight((short) 700);
	}

	public void postProcessXLS(Object document) throws IOException {
		int cont = 0;
		int auxCont = 0;
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		String logo = externalContext.getRealPath("");
		
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		int numRow = listaResultado.size();
		for (int i = 0; i < listaVisible.size(); i++) {
			sheet.autoSizeColumn(i);
			if (listaVisible.get(i) && i <= 12) {
				cont += 1; // ver la posicion de la columna foto en la lista
			}
		}
		if(listaVisible.get(12)){
			while (auxCont < numRow) {
				try {
					FileInputStream inputStream = new FileInputStream(logo
							+ listaResultado.get(auxCont).getFoto());
					byte[] colaboradorFile = IOUtils.toByteArray(inputStream);
					int pictureIdx = wb.addPicture(colaboradorFile, XSLFPictureData.PICTURE_TYPE_JPEG);
	
					if(listaResultado.get(auxCont).getFoto().endsWith("png")){
						 pictureIdx = wb.addPicture(colaboradorFile,
								XSLFPictureData.PICTURE_TYPE_PNG);
					}
					
					if(listaResultado.get(auxCont).getFoto().endsWith("gif")){
						 pictureIdx = wb.addPicture(colaboradorFile,
								XSLFPictureData.PICTURE_TYPE_GIF);
					}
					
					CreationHelper helper = wb.getCreationHelper();
					Drawing drawing = sheet.createDrawingPatriarch();
					ClientAnchor anchor = helper.createClientAnchor();
					inputStream.close();
					anchor.setCol1(cont - 1);
					anchor.setRow1(auxCont + 1);
					anchor.setCol2(cont);
					anchor.setRow2(auxCont + 2);
					drawing.createPicture(anchor, pictureIdx);
	
					auxCont += 1;
				} catch (IOException e) {
					log.info("El usuario no tiene foto en la ruta: "+ listaResultado.get(auxCont).getFoto());
					auxCont += 1;
				}
			}
		}
	}

	// Getters & setters
	public List<String> getNivelesGCM() {
		return nivelesGCM;
	}

	public void setNivelesGCM(List<String> nivelesGCM) {
		this.nivelesGCM = nivelesGCM;
	}

	public FiltroGenerarInformacion getFiltroBusqueda() {
		return filtroBusqueda;
	}

	public void setFiltroBusqueda(FiltroGenerarInformacion filtroBusqueda) {
		this.filtroBusqueda = filtroBusqueda;
	}

	public List<Colaborador> getListaResultado() {
		return listaResultado;
	}

	public void setListaResultado(List<Colaborador> listaResultado) {
		this.listaResultado = listaResultado;
	}

	public String getGcmCrud() {
		return gcmCrud;
	}

	public void setGcmCrud(String gcmCrud) {
		this.gcmCrud = gcmCrud;
	}

	public int getAux() {
		return aux;
	}

	public void setAux(int aux) {
		this.aux = aux;
	}

	public List<Boolean> getListaVisible() {
		return listaVisible;
	}

	public void setListaVisible(List<Boolean> listaVisible) {
		this.listaVisible = listaVisible;
	}

	public List<Capacidad> getCapacidades() {
		return capacidades;
	}

	public void setCapacidades(List<Capacidad> capacidades) {
		this.capacidades = capacidades;
	}

	public List<Promocion> getPromociones() {
		return promociones;
	}

	public void setPromociones(List<Promocion> promociones) {
		this.promociones = promociones;
	}

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

}
