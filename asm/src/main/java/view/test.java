package view;

import entity.Advertising;

public class test {
	public static void main(String[] args) {
		Advertising a = AllDAO.daoAdver.adver().get(0);
		System.err.println(a.getId());
		
	}
}
