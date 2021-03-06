/*
 * Copyright (c) 2015, Andreas Reuter, Freie Universität Berlin 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 * 
 * */
package main.java.miro.validator.export.json;

import java.lang.reflect.Type;

import main.java.miro.validator.types.CertificateObject;
import main.java.miro.validator.types.ResourceCertificateTree;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ResourceCertificateTreeSerializer implements JsonSerializer<ResourceCertificateTree>{

	public JsonElement serialize(ResourceCertificateTree src, Type typeOfSrc,
			JsonSerializationContext context) {
		
		JsonObject treeJson = new JsonObject();
		
		treeJson.add("name", new JsonPrimitive(src.getName()));
		treeJson.add("date", new JsonPrimitive(src.getTimeStamp().toString()));
		treeJson.add("trustAnchor", context.serialize(src.getTrustAnchor(), CertificateObject.class));
		
		
		return treeJson;
	}
	

}
