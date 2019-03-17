package dict;

import java.util.ArrayList;
import java.util.Scanner;

public class dictionary
{

	static ArrayList<database> list = null;
	static Scanner scanner = null;

	public static void main(String[] args)
	{
		System.out.println("실행되었습니다");
		scanner = new Scanner(System.in);
		while (true)
		{
			String input_string = scanner.nextLine();
			load_method(input_string);
		}
	}

	public static void read_dbfile(String filename)
	{
		read_data readdata = new read_data();
		readdata.ReadFile(filename);
		list = readdata.GetLIST();
	}

	public static int get_size()
	{
		return list.size();
	}

	public static void load_method(String input)
	{
		String[] separated_string = separate_string(input);
		String method = separated_string[0];
		String value = null;

		switch (method)
		{
		case "read":
			value = separated_string[1];
			read_dbfile(value);
			break;
		case "size":
			System.out.println(get_size());
			break;
		case "find":
			value = separated_string[1].toLowerCase();

			boolean check_duplication = false;
			int index = BinarySearch(value, 0, list.size() - 1);

			if (list.get(index).word.toLowerCase().equals(value))
				check_duplication = true;
			else
				check_duplication = false;

			if (check_duplication)
			{
				int[] int_index = check_redundancy(index, value);

				int first_idx = int_index[0];
				int end_idx = int_index[1];
				int num = end_idx - first_idx + 1;

				System.out.println("Found " + num + " items.");
				for (int i = first_idx; i <= end_idx; i++)
					System.out.println(list.get(i).word + " " + list.get(i).category + " " + list.get(i).description);

			} else
			{
				System.out.println("Not found.");
				if (index != -1)
				{
					System.out.println(list.get(index).word + " " + list.get(index).category);
					System.out.println("- - -");
					System.out.println(list.get(index + 2).word + " " + list.get(index + 2).category);
				} else if (index == -1)
				{
					System.out.println("- - -");
					System.out.println(list.get(index + 2).word + " " + list.get(index + 2).category);
				} else if (index == list.size() - 2)
				{
					System.out.println(list.get(index).word + " " + list.get(index).category);
					System.out.println("- - -");
				}
			}
			break;
		case "exit":
			scanner.close();
			System.exit(0);
			break;
		default:
			break;
		}
	}

	public static String[] separate_string(String string)
	{
		String[] for_separation = string.split(" ");

		if (for_separation.length > 1)
		{
			for (int i = 2; i < for_separation.length; i++)
			{
				for_separation[1] = for_separation[1] + "" + for_separation[i];
			}
		}

		return for_separation;

	}

	static int BinarySearch(String word, int start, int end)
	{
		int ret = 0;
		int middle;
		int result = 0;

		if (start <= end)
		{
			middle = (start + end) / 2;

			if (end - start == 1)
			{

				int res1, res2;
				res1 = word.toLowerCase().compareTo(list.get(start).word.toLowerCase());
				res2 = word.toLowerCase().compareTo(list.get(end).word.toLowerCase());

				if (res1 == 0)
				{
					result = res1;
					middle = start;
				} else if (res2 == 0)
				{
					result = res2;
					middle = end;
				} else
				{
					if (res1 >= res2)
						middle = start;
					else
						middle = end;
					ret = middle;
					return ret - 1;
				}
			} else if (start == end)
				return start - 1;
			else
				result = word.toLowerCase().compareTo(list.get(middle).word.toLowerCase());

			if (result == 0)
				ret = middle;
			else if (result < 0)
				ret = BinarySearch(word, start, middle - 1);
			else
				ret = BinarySearch(word, middle + 1, end);
		}

		return ret;

	}

	static int[] check_redundancy(int idx, String word)
	{

		int first = -1, end = -1, i = -1;
		int[] index = new int[2]; // first, end
		boolean first_check = false;
		if (idx <= 20)
		{
			i = 0;
			for (; i < idx + 20; i++)
			{
				if (word.toLowerCase().equals(list.get(i).word.toLowerCase()) && !first_check)
				{
					first = i;
					first_check = true;
				}
				if (first != -1 && !(word.toLowerCase().equals(list.get(i).word.toLowerCase())))
				{
					end = i - 1;
					break;
				}
			}
		} else if (idx >= list.size() - 21)
		{
			i = idx - 20;
			for (; i < list.size(); i++)
			{
				if (word.toLowerCase().equals(list.get(i).word.toLowerCase()) && !first_check)
				{
					first = i;
					first_check = true;
					if (i == (list.size() - 1))
					{
						end = i;
						break;
					}
				}
				if (first != -1 && !(word.toLowerCase().equals(list.get(i).word.toLowerCase())))
				{
					end = i - 1;
					break;
				}
			}

		} else
		{
			i = idx - 20;
			for (; i < idx + 20; i++)
			{
				if (word.toLowerCase().equals(list.get(i).word.toLowerCase()) && !first_check)
				{
					first = i;
					first_check = true;
				}
				if (first != -1 && !(word.toLowerCase().equals(list.get(i).word.toLowerCase())))
				{
					end = i - 1;
					break;
				}
			}
		}

		index[0] = first;
		index[1] = end;

		return index;
	}

}
