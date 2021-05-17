import json
import os
from typing import Dict

from flask import Flask, render_template, request, redirect, url_for, flash, abort, session, jsonify
from werkzeug.utils import secure_filename

app = Flask(__name__)
app.secret_key = "asdflkjasd;oiuqpweoiurg"


@app.route('/')
def home():
    return render_template("home.html", name="Zhentao", codes=session.keys())


@app.route("/your-url", methods=["GET", "POST"])
def your_url():
    if request.method == "POST":

        # initialize the json dict
        urls: Dict[str, Dict[str, str]] = dict()
        if os.path.exists("urls.json"):
            with open("urls.json", "r") as fh:
                urls = json.load(fh)

        # check duplication
        if request.form['code'] in urls:
            flash("That short name has been taken, please select another one")
            return redirect(url_for("home"))

        # dump
        print(request.form.keys())
        if "url" in request.form.keys():
            # update json
            urls[request.form['code']] = {"url": request.form['url']}

        else:
            f = request.files['file']
            full_name = request.form['code'] + secure_filename(filename=f.filename)
            f.save(os.path.join(
                "/Users/zhentaoxu/Documents/LinkedInLearningProjects/Flask_Essential_Training/url-shortener/static/user_files",
                full_name))
            urls[request.form['code']] = {"file": full_name}

        with open("urls.json", "w") as fh:
            print(session)
            session[request.form['code']] = True
            json.dump(urls, fh)

        # rendering
        return render_template("your_url.html",
                               code=request.form['code'])
    else:
        return redirect(url_for("home"))


@app.route("/<string:code>")
def redirect_to_url(code):
    if os.path.exists("urls.json"):
        with open("urls.json", "r") as fh:
            urls = json.load(fh)
            if code in urls:
                if "url" in urls[code]:
                    return redirect(urls[code]['url'])
                else:
                    return redirect(url_for("static", filename="user_files/" + urls[code]['file']))
    return abort(404)


@app.errorhandler(404)
def page_not_found(error):
    return render_template("page_not_found.html", error=error), 404


@app.route("/api")
def session_api():
    return jsonify(list(session.keys()))
