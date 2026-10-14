<escape>export default {
async fetch(request, env) {
const url = new URL(request.url);
const corsHeaders = {
"Access-Control-Allow-Origin": "*",
"Access-Control-Allow-Methods": "GET, POST, OPTIONS",
"Access-Control-Allow-Headers": "Content-Type"
};

const json = (data, status = 200) =>
  new Response(JSON.stringify(data), {
    status,
    headers: {
      ...corsHeaders,
      "Content-Type": "application/json; charset=utf-8"
    }
  });

if (request.method === "OPTIONS") {
  return new Response(null, { headers: corsHeaders });
}

if (url.pathname === "/api/health") {
  return json({ ok: true, app: "Alqassam AI" });
}

if (url.pathname === "/api/chat" && request.method === "POST") {
  try {
    const body = await request.json();
    const message = String(body.message || "").trim();

    if (!message) {
      return json({ error: "اكتب رسالتك أولًا." }, 400);
    }

    if (!env.AI) {
      return json({
        error: "لم يتم إعداد Workers AI binding باسم AI في Cloudflare."
      }, 503);
    }

    const result = await env.AI.run(
      "@cf/meta/llama-3.1-8b-instruct",
      {
        messages: [
          {
            role: "system",
            content: "أنت القسام AI، مساعد عربي مفيد. أجب باللغة العربية بوضوح، وساعد المستخدم في البرمجة وتصميم التطبيقات."
          },
          {
            role: "user",
            content: message
          }
        ]
      }
    );

    return json({
      reply: result.response || "لم أتمكن من إنشاء رد. حاول مرة أخرى."
    });
  } catch (error) {
    return json({
      error: "حدث خطأ أثناء معالجة الطلب. تحقق من إعدادات Cloudflare AI."
    }, 500);
  }
}

if (url.pathname === "/") {
  return new Response("القسام AI يعمل", {
    headers: { "Content-Type": "text/plain; charset=utf-8" }
  });
}

return json({ error: "المسار غير موجود." }, 404);

}
};</escape>
