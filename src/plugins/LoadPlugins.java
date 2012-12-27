package plugins;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import console.ICommand;

public class LoadPlugins {
	static ICommand loadedCommands[] = new ICommand[64];
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void load()throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		File thePath = new File("plugin");
		String[] relativeJarNames = thePath.list(new FilenameFilter(){

			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".jar");
			}
		});
		int arrayIndex=0;
		for(String name:relativeJarNames){
			System.out.printf("processing %s \n", name);
			File f = new File(thePath.getAbsolutePath()+"\\"+name);
			JarFile jf = new JarFile(f);
			for (Enumeration<JarEntry> entries = jf.entries(); entries.hasMoreElements(); ) {
				JarEntry entry = entries.nextElement();
				String entryName = entry.getName();
				if(entryName.endsWith(".class") == false) {
					continue;
				}
				String className = entryName.replaceAll("/", ".");
				className = className.substring(0, className.length() - ".class".length());
				
				URLClassLoader loader = URLClassLoader.newInstance(new URL[] { f.toURI().toURL() });
				ICommand sub = (ICommand) loader.loadClass(className).newInstance();
				System.out.println("\tCommand '"+sub.getCommandName()+"' loaded from "+jf.getName()+" (class '"+className+"')");
				loadedCommands[arrayIndex] = sub;
				arrayIndex++;
			}
			
			jf.close();
		}
	}
	public static void doPluginCommand(String command,String arguments[]){
		for(ICommand com:loadedCommands){
			if(com.getCommandName().equals(command)){
				com.doCommand(arguments);
				break;
			}
		}
	}

}
