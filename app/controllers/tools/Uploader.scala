package controllers.tools

import java.io.File

import auth.UserAction
import org.denigma.semantic.controllers.sync.WithSyncWriter
import org.denigma.semantic.controllers.{SimpleQueryController, UpdateController}
import org.denigma.semantic.files.SemanticFileParser
import play.api.Play
import play.api.Play.current
import play.api.libs.json.{JsObject, Json}
import play.api.mvc._

/**
 * Handler everything about uploading
 */
object Uploader extends Controller with SemanticFileParser with WithSyncWriter  with SimpleQueryController with UpdateController{


//  def imageUpload = Action(parse.temporaryFile) { request =>
//    request.body.moveTo(new File("/tmp/picture/uploaded"))
//    Ok("File uploaded")
//  }


  /*
   TODO: test upload code
   WARNING: NOT TESTED
    */
  def imageUpload = UserAction(parse.multipartFormData) { implicit request =>
    import play.api.libs.json.Json._
    val relativePath = "assets/users/" +  request.username.getOrElse("guest")
    val uploads = Play.getFile(relativePath)
    val p = uploads.getAbsolutePath
    val jsFiles: scala.List[JsObject] = request.body.files.map{ f=>
      val fname = f.filename

      val name = p+"/"+fname
      val file = new File(name)

      f.ref.moveTo(file,replace = true)

     Json.obj(
        "name"->f.filename,
        "size"->file.length(),
        "url"-> (request.domain+relativePath+"/"+f.filename)
      )

    }.toList
    val res = Json.obj("files" -> Json.toJson(jsFiles))
    Ok(res)
  }



  /*
   TODO: test upload code
   WARNING: NOT TESTED
    */
  def upload = UserAction(parse.multipartFormData) { implicit request =>
    import play.api.libs.json.Json._
    val uploads = Play.getFile("public/uploads/")
    val p = uploads.getAbsolutePath
    val files: scala.List[JsObject] = request.body.files.map{ f=>
      val fname = f.filename

      val name = p+"/"+fname
      val file = new File(name)

      f.ref.moveTo(file,replace = true)

      val obj: JsObject = Json.obj(
        "name"->f.filename,
        "size"->file.length(),
        "url"-> (request.domain+"assets/uploads/"+f.filename)
      )
      val uplCon = "http://webintelligence.eu/uploaded/"
      val pr = this.parseFile(file,uplCon)

      if(pr.isFailure) obj + ("error"->toJson("WRONG SEMANTIC WEB FILE"))  else obj

    }.toList
    val res = Json.obj("files" -> Json.toJson(files))
    Ok(res)
  }




  def images= UserAction {
    implicit request=>

      val imgs = Json.arr(
        Json.obj(
          "image" -> "/longevity_ukraine.svg",
          "folder" -> "assets/images/longevity.org.ua"
        ),
        Json.obj(
          "image" -> "/longevity_ukraine.svg",
          "folder" -> "assets/images/longevity.org.ua"
        )

      )

      Ok(imgs).as("application/json")
  }
}
