/* Released under the GPL2. See license.txt for details. */
package br.com.centralit.nagios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PerformanceData
{
	TreeMap<String, PerformanceDataPerElement> map = new TreeMap<String, PerformanceDataPerElement>();

	public PerformanceData()
	{
	}

	public PerformanceData(String fileName) throws Exception
	{
                String line;
                BufferedReader in = new BufferedReader(new FileReader(fileName));

		while((line = in.readLine()) != null)
		{
			// thegate | HTTP;time;0.009249|0.009249
			String [] parts = line.split(";");

			PerformanceDataPerElement newElement = map.get(parts[0]);
			if (newElement == null)
			{
				newElement = new PerformanceDataPerElement();
				map.put(parts[0], newElement);
			}

			DataSource dataSource = newElement.add(parts[1]);

			String [] values = parts[2].split("\\|");
			for(String value : values)
				dataSource.add(Double.valueOf(value));
		}
	}

	public PerformanceDataPerElement get(String element)
	{
		return map.get(element);
	}

	public PerformanceDataPerElement get(String host, String service)
	{
		if (service != null)
			return map.get(host + " | " + service);

		return map.get(host);
	}

	public void add(String element, String nagiosPerfomanceDataLine, String checkTime)
	{
		PerformanceDataPerElement current = map.get(element);
		if (current == null)
		{
			current = new PerformanceDataPerElement();
			map.put(element, current);
		}

		if (current.getCheckTime().equals(checkTime) == true)
			return;
		current.setCheckTime(checkTime);

		// rta=0.168000ms;1000.000000;2000.000000;0.000000 pl=0%;20;60;0
		String [] sets = nagiosPerfomanceDataLine.split(" ");
		for(String currentSet : sets)
		{
			int is = currentSet.indexOf("=");
			if (is == -1)
				continue;
			String name = currentSet.substring(0, is);

			int data = is + 1;
			while(data < currentSet.length() && (currentSet.charAt(data) == '.' || currentSet.charAt(data) == '-' || (currentSet.charAt(data) >= '0' && currentSet.charAt(data) <= '9')))
				data++;

			String valueStr = currentSet.substring(is + 1, data).trim();
			if (valueStr.equals("") == false)
			{
				double value = Double.valueOf(valueStr);

				current.add(name, value);

				String afterValue = currentSet.substring(data);
				int semiColon = afterValue.indexOf(";");
				if (semiColon > 0)
				{
					String unit = afterValue.substring(0, semiColon);

					current.setDataSourceUnit(name, unit);
				}
				else if (afterValue.length() > 0 && semiColon != 0)
				{
					current.setDataSourceUnit(name, afterValue);
				}
			}
		}
	}

	public List<PerformanceDataPerElement> getAllElements()
	{
		return new ArrayList<PerformanceDataPerElement>(map.values());
	}

	void writeLine(BufferedWriter out, String line) throws Exception
	{
		out.write(line, 0, line.length());
		out.newLine();
	}

	public void dump(String fileName) throws Exception
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

		for(Map.Entry<String, PerformanceDataPerElement> entry : map.entrySet())
		{
			for(DataSource currentDataSource : entry.getValue().getAllDataSources())
			{
				StringBuilder output = new StringBuilder();
				boolean first = true;

				output.append(entry.getKey());
				output.append(";");
				output.append(currentDataSource.getDataSourceName());
				output.append(";");

				for(Double currentData : currentDataSource.getData())
				{
					if (first)
						first = false;
					else
						output.append("|");

					output.append("" + currentData);
				}

				writeLine(out, output.toString());
			}
		}

		out.close();
	}
}
