package org.hawk.ttc2018.metamodels;

import java.io.InputStream;

public final class Metamodels {

	private Metamodels() {}

	public static InputStream getEcoreMetamodel() {
		return Metamodels.class.getResourceAsStream("Ecore.ecore");
	}

	public static InputStream getChangeSequenceMetamodel() {
		return Metamodels.class.getResourceAsStream("NMetaChanges.ecore");
	}

	public static InputStream getSocialMediaMetamodel() {
		return Metamodels.class.getResourceAsStream("social_network.ecore");
	}

}
