package view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import metrics.ATFD;
import metrics.NOA;
import metrics.NOAM;
import metrics.NOM;
import metrics.WMC;
import metrics.WOC;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import ast.ASTReader;
import ast.CompilationUnitCache;
import ast.SystemObject;

public class MetricsAction implements IObjectActionDelegate {

	private IWorkbenchPart part;
	private ISelection selection;

	private IJavaProject selectedProject;
	private IPackageFragmentRoot selectedPackageFragmentRoot;
	private IPackageFragment selectedPackageFragment;
	private ICompilationUnit selectedCompilationUnit;
	private IType selectedType;
	private IMethod selectedMethod;

	public void run(IAction arg0) {
		try {
			CompilationUnitCache.getInstance().clearCache();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				Object element = structuredSelection.getFirstElement();
				if (element instanceof IJavaProject) {
					selectedProject = (IJavaProject) element;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedType = null;
					selectedMethod = null;
				} else if (element instanceof IPackageFragmentRoot) {
					IPackageFragmentRoot packageFragmentRoot = (IPackageFragmentRoot) element;
					selectedProject = packageFragmentRoot.getJavaProject();
					selectedPackageFragmentRoot = packageFragmentRoot;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedType = null;
					selectedMethod = null;
				} else if (element instanceof IPackageFragment) {
					IPackageFragment packageFragment = (IPackageFragment) element;
					selectedProject = packageFragment.getJavaProject();
					selectedPackageFragment = packageFragment;
					selectedPackageFragmentRoot = null;
					selectedCompilationUnit = null;
					selectedType = null;
					selectedMethod = null;
				} else if (element instanceof ICompilationUnit) {
					ICompilationUnit compilationUnit = (ICompilationUnit) element;
					selectedProject = compilationUnit.getJavaProject();
					selectedCompilationUnit = compilationUnit;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedType = null;
					selectedMethod = null;
				} else if (element instanceof IType) {
					IType type = (IType) element;
					selectedProject = type.getJavaProject();
					selectedType = type;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedMethod = null;
				} else if (element instanceof IMethod) {
					IMethod method = (IMethod) element;
					selectedProject = method.getJavaProject();
					selectedMethod = method;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedType = null;
				}
				IWorkbench wb = PlatformUI.getWorkbench();
				IProgressService ps = wb.getProgressService();
				ps.busyCursorWhile(new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						if (ASTReader.getSystemObject() != null
								&& selectedProject.equals(ASTReader
										.getExaminedProject())) {
							new ASTReader(selectedProject, ASTReader
									.getSystemObject(), monitor);
						} else {
							new ASTReader(selectedProject, monitor);
						}
						SystemObject system = ASTReader.getSystemObject();
						ATFD atfd = new ATFD(system);
						try {
							writeXLSFile(atfd.resultSet(), "atfd");
						} catch (IOException e) {
							e.printStackTrace();
						}

						NOA noa = new NOA(system);
						try {
							writeXLSFile(noa.resultSet(), "noa");
						} catch (IOException e) {
							e.printStackTrace();
						}
						NOAM noam = new NOAM(system);
						try {
							writeXLSFile(noam.resultSet(), "noam");
						} catch (IOException e) {
							e.printStackTrace();
						}
						NOM nom = new NOM(system);
						try {
							writeXLSFile(nom.resultSet(), "nom");
						} catch (IOException e) {
							e.printStackTrace();
						}
						WMC wmc = new WMC(system);
						try {
							writeXLSFile(wmc.resultSet(), "wmc");
						} catch (IOException e) {
							e.printStackTrace();
						}
						WOC woc = new WOC(system);
						try {
							writeXLSFile(woc.resultSet(), "woc");
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (selectedPackageFragmentRoot != null) {
							// package fragment root selected
						} else if (selectedPackageFragment != null) {
							// package fragment selected
						} else if (selectedCompilationUnit != null) {
							// compilation unit selected
						} else if (selectedType != null) {
							// type selected
						} else if (selectedMethod != null) {
							// method selected
						} else {
							// java project selected
						}
					}
				});
			}
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.part = targetPart;
	}

	public void writeXLSFile(Map<String, Double> data, String fileName)
			throws IOException {

		String excelFileName = "D:/Temp/" + fileName + ".xls";// name of excel
																// file

		String sheetName = "Sheet1";// name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		// Create a new row in current sheet
		Row row = sheet.createRow(0);

		// Create a new cell in current row
		Cell cell = row.createCell(0);

		// Set value to new value
		cell.setCellValue("");
		// iterating r number of rows
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {

			Row row1 = sheet.createRow(rownum++);
			Double metricValue = data.get(key);
			Cell className = row1.createCell(0);
			className.setCellValue(key);
			int cellnum = 1;
			Cell cell1 = row1.createCell(cellnum);
			cell1.setCellValue(metricValue);
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		// write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
}
