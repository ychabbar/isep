/*package com.isepfm.comments;

import java.sql.Date;

import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ITrackDao;
import com.isepfm.service.IUsersDao;

public class commentaires {
	
	
	private String albumName;
	private String userName;
	private String contenu;
	private Date dateCom;
	
	public commentaires(String albumName, String userName, String contenu, Date dateCom) {
		this.albumName = albumName;
		this.userName = userName;
		this.contenu = contenu;
		this.dateCom = dateCom;
	}
			
	private static final String CONTENU_INVALIDE = "1"; 
			  
	
	public boolean isValid()
	{
		if(albumName == null || contenu == null)
		
		//return !(this.albumName empty($this->auteur) || empty($this->contenu));
	}
			  
			/*  // SETTERS
			  
			  public function setNews($news)
			  {
			    $this->news = (int) $news;
			  }
			  
			  public function setAuteur($auteur)
			  {
			    if (!is_string($auteur) || empty($auteur))
			    {
			      $this->erreurs[] = self::AUTEUR_INVALIDE;
			    }
			    else
			    {
			      $this->auteur = $auteur;
			    }
			  }
			  
			  public function setContenu($contenu)
			  {
			    if (!is_string($contenu) || empty($contenu))
			    {
			      $this->erreurs[] = self::CONTENU_INVALIDE;
			    }
			    else
			    {
			      $this->contenu = $contenu;
			    }
			  }
			  
			  public function setDate(\DateTime $date)
			  {
			    $this->date = $date;
			  }
			  
			  // GETTERS
			  
			  public function news()
			  {
			    return $this->news;
			  }
			  
			  public function auteur()
			  {
			    return $this->auteur;
			  }
			  
			  public function contenu()
			  {
			    return $this->contenu;
			  }
			  
			  public function date()
			  {
			    return $this->date;
			  }
			}

}
*/