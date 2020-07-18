import java.util.LinkedList;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class KeyWordListener extends ListenerAdapter
{
	
	//happiness, knowledge, remembering things, sadness (happiness -1), liking random people
	
	private LinkedList<String> facts, memories;
	
	public KeyWordListener()
	{
		facts = new LinkedList<String>();
		memories = new LinkedList<String>();
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		String msg = e.getMessage().getContentRaw();
		String[] arguments = msg.split(" ");
		
		if(e.getAuthor().equals(e.getJDA().getSelfUser())) //globby ignores itself
			return;
		
		if(isHappy(msg) && isNotCheated(msg))
			e.getChannel().sendMessage("Happiness +1").queue();
		
		if(hasLearned(msg) && arguments.length > 3)
		{
			facts.add(msg);
			e.getChannel().sendMessage("I learned that " + msg).queue();
			e.getChannel().sendMessage("Knowledge +1").queue();
		}
		
		if(arguments[0].equalsIgnoreCase("remember"))
		{
			String whatToRemember = "";
			
			for(int i = 1; i < arguments.length; i++)
				whatToRemember += " " + arguments[i];
			
			memories.add(whatToRemember);
			e.getChannel().sendMessage("Globby will remember that " + whatToRemember).queue();
		}
		
		if(isSad(msg) && isNotCheated(msg))
			e.getChannel().sendMessage("Happiness -1").queue();
		
		if(Math.random() < 0.05 && !e.getAuthor().getId().equals("596142557348626481") && !isSad(msg))
			e.getChannel().sendMessage("Globby likes " + e.getMember().getNickname() + "!").queue();
		
		if(Math.random() < 0.05 && memories.size() > 0)
			e.getChannel().sendMessage("I remember that " + memories.get((int) Math.random()*memories.size())).queue();
		
		if(Math.random() < 0.05 && facts.size() > 0)
			e.getChannel().sendMessage("Did you know that " + facts.get((int) Math.random()*facts.size()) + "?").queue();
		
		if((e.getAuthor().getId().equals("475859944101380106") || e.getAuthor().getId().equals("544620856769511437")) && e.getMessage().getContentRaw().equalsIgnoreCase("Globby reboot"))
		{
			e.getChannel().sendMessage("Globby feels tired... powering down...").queue();
			Main.jda.shutdown();
			System.exit(0);
		}
	}
	
	private boolean hasLearned(String msg)
	{
		String[] factWords = {"fact", "truth", "know", "knowledge", "true"};
		String[] arguments = msg.split(" ");
		
		for(int i = 0; i < factWords.length; i++)
		{
			for(int j = 0; j < arguments.length; j++)
			{
				if(arguments[j].equalsIgnoreCase(factWords[i]))
					return true;
			}
		}
		
		return false;
	}
	
	private boolean isSad(String msg)
	{
		String[] sadWords = {"sad", "upset", "frustrated", "angry", "bad"};
		String[] arguments = msg.split(" ");
		
		for(int i = 0; i < sadWords.length; i++)
		{
			for(int j = 0; j < arguments.length; j++)
			{
				if(sadWords[i].equalsIgnoreCase(arguments[j]))
					return true;
			}
		}
		
		return false;
	}
	
	private boolean isHappy(String msg)
	{
		String[] happyWords = {"happy", "glad", "great", "amazing", "brilliant", "love"};
		String[] arguments = msg.split(" ");
		
		for(int i = 0; i < happyWords.length; i++)
		{
			for(int j = 0; j < arguments.length; j++)
			{
				if(arguments[j].equalsIgnoreCase(happyWords[i]))
					return true;
			}
		}
		
		return false;
	}
	
	private boolean isNotCheated(String msg)
	{
		String[] cheatWords = {"not", "isn't", "isnt", "doesn't", "doesnt", "won't", "wont", "aren't", "arent", "opposite"};
		String[] arguments = msg.split(" ");
		
		for(int i = 0; i < cheatWords.length; i++)
		{
			for(int j = 0; j < arguments.length; j++)
			{
				if(cheatWords[i].equalsIgnoreCase(arguments[j]))
					return false;
			}
		}
		
		return true;
	}
}
