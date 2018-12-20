package ttc2018;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import Changes.ChangesPackage;
import SocialNetwork.Comment;
import SocialNetwork.Post;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import SocialNetwork.Submission;
import SocialNetwork.User;

public class ModelConverter {

	public static void main(String[] args) throws IOException {
		ModelConverter converter = new ModelConverter();
		
		converter.load(1);
	}

    private SocialNetworkRoot loadSocialNetworkFile(int size) throws IOException {
    	ResourceSet repository;
    	repository = new ResourceSetImpl();
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		repository.getPackageRegistry().put(SocialNetworkPackage.eINSTANCE.getNsURI(), SocialNetworkPackage.eINSTANCE);
		repository.getPackageRegistry().put(ChangesPackage.eINSTANCE.getNsURI(), ChangesPackage.eINSTANCE);

    	Resource resource = repository.getResource(URI.createFileURI(new File("../../models/" + size + "/initial.xmi").getCanonicalPath()), true);
    	return (SocialNetworkRoot) resource.getContents().get(0);
    }

    private void load(int size) throws IOException {
    	SocialNetworkRoot root = loadSocialNetworkFile(size);
    	
    	List<User> users = new ArrayList<>();
    	List<Post> posts = new ArrayList<>();
    	List<Comment> comments = new ArrayList<>();
    	
		root.eAllContents().forEachRemaining(x -> {
			if (x instanceof Submission) {
								
			}			
			
			if (x instanceof User) {
				users.add((User) x);
			} else if (x instanceof Post) {
				posts.add((Post) x);
			} else if (x instanceof Comment) {
				comments.add((Comment) x);
			}
    	});
		
		System.out.println(users.size() + " users");
		System.out.println(posts.size() + " posts");
		System.out.println(comments.size() + " comments");
		
		users.forEach(u -> {
				System.out.println(u.getId() + "," + u.getName());
				// friends
				u.getFriends().forEach(f ->
					System.out.println(u.getId() + "," + f.getId())
				);
				// likes
				u.getLikes().forEach(l ->
					System.out.println(u.getId() + "," + l.getId())
				);
			}
		);
		comments.forEach(c ->
			System.out.println(
				c.getId() + "," + 
				c.getTimestamp() + "," + 
				c.getContent() +
				c.getCommented().getId() + "," + 
				c.getPost().getId()
			)
		);
		posts.forEach(p ->
			System.out.println(
				p.getId() + "," + 
				p.getTimestamp() + "," + 
				p.getContent()
			)
		);
    }

}
