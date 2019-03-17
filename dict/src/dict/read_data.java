package dict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class read_data
{
	ArrayList<database> LIST = new ArrayList<database>();

	public void ReadFile(String filename)
	{

		String st2 = null;
		database[] database_list = null;
		ArrayList<String> replace_string = new ArrayList<String>();
		try
		{
			File file = new File(filename);

			BufferedReader br = new BufferedReader(new FileReader(file));

			st2 = br.readLine();

			while (st2 != null)
			{
				if (!st2.equals(""))
					replace_string.add(st2);
				st2 = br.readLine();

			}
			br.close();

			database_list = new database[replace_string.size()];

			for (int i = 0; i < replace_string.size(); i = i + 1)
			{
				database_list[i] = new database(Getword(replace_string.get(i)), Getcategory(replace_string.get(i)),
						Getdescription(replace_string.get(i)));

				LIST.add(database_list[i]);

			}
			System.out.println("completed");
		} catch (Exception e)
		{
			e.printStackTrace();

		}
	}

	public String Getword(String str)
	{
		String[] separated_words = str.split(" \\(");

		return separated_words[0];
	}

	public String Getcategory(String str)
	{
		String[] separated_category = str.split("\\(");
		String separated_string = separated_category[1];

		separated_category = separated_string.split("\\)");
		separated_string = "(" + separated_category[0] + ")";

		return separated_string;
	}

	public String Getdescription(String str)
	{
		String[] separated_description = str.split("\\)");
		String final_description = null;
		if (separated_description.length == 1)
		{
			final_description = "";
		} else
		{
			final_description = separated_description[1];

			for (int i = 2; i < separated_description.length; i++)
			{
				final_description = final_description + ")" + separated_description[i];
			}
		}
		return final_description;
	}

	public ArrayList<database> GetLIST()
	{
		return this.LIST;
	}
}
