
package me.xxsniperzzxxsd.infoboard.Variables;

import me.zford.jobs.Jobs;

import org.bukkit.entity.Player;


public class JobsVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = "Hi";
		newString = string;

		if (newString.contains("<jobsin>"))
			newString = newString.replaceAll("<jobsin>", String.valueOf(Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).size()));

		if (newString.contains("<jobstitle"))
		{
			int i = Integer.valueOf(newString.split("<jobstitle")[1].split(">")[0]) - 1;

			if (Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).size() >= i + 1)
			{
				newString = newString.replaceAll("<jobstitle" + (i + 1) + ">", String.valueOf(Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).get(i).getJobName()));
			} else
			{
				newString = newString.replaceAll("<jobstitle" + (i + 1) + ">", "Join a Job");
			}
		}

		if (newString.contains("<jobslevel"))
		{
			int i = Integer.valueOf(newString.split("<jobslevel")[1].split(">")[0]) - 1;

			if (Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).size() >= i + 1)
			{
				newString = newString.replaceAll("<jobslevel" + (i + 1) + ">", String.valueOf(Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).get(i).getLevel()));
			} else
			{
				newString = newString.replaceAll("<jobslevel" + (i + 1) + ">", "0");
			}
		}
		if (newString.contains("<jobsexp"))
		{
			int i = Integer.valueOf(newString.split("<jobsexp")[1].split(">")[0]) - 1;

			if (Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).size() >= i + 1)
			{
				newString = newString.replaceAll("<jobsexp" + (i + 1) + ">", String.valueOf(Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).get(i).getExperience()));
			} else
			{
				newString = newString.replaceAll("<jobsexp" + (i + 1) + ">", "0");
			}
		}
		if (newString.contains("<jobsmaxlevel"))
		{
			int i = Integer.valueOf(newString.split("<jobsmaxlevel")[1].split(">")[0]) - 1;

			if (Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).size() >= i + 1)
			{
				newString = newString.replaceAll("<jobsmaxlevel" + (i + 1) + ">", String.valueOf(Jobs.getJob(Jobs.getJobsDAO().getAllJobs(Jobs.getPlayerManager().getJobsPlayer(player.getName())).get(i).getJobName()).getMaxLevel()));
			} else
			{
				newString = newString.replaceAll("<jobsmaxlevel" + (i + 1) + ">", "0");
			}
		}

		return newString;
	}
}
