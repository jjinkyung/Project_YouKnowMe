<template>
  <div class="landing-page">
    <video class="landing-video" muted autoplay loop>
      <source src="~@/assets/land/landing-background.mp4" type="video/mp4">
    </video>
    <img class="landing-text" src="@/assets/land/landing-text.png" alt="landing-text">
    <div class="avatar">
      <img class="me" src="@/assets/land/character_woman.png" alt="me">
      <img class="you-know" src="@/assets/land/character_man.png" alt="you-know">
    </div>
  </div>
  <cite class="img-cite">&copy; The Jazz Hop Café</cite>
</template>
<script>
export default {
  name: 'LandingPage',
}
</script>

<style>
.landing-page {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  /* background-size: cover; */
  /* background-position: center; */
  /* background-image: url("@/assets/land/landing_background.png"); */
}
.landing-video {
  width: 100vw;
  height: 100vh;
  object-fit: cover;
}
.landing-text {
  position: absolute;
  /* width: 210px; */
  /* height: 271px; */
  height: 30%;
  right: 50%;
  top: 0px;
  transform: translate(50%, 0%);
  filter: drop-shadow(0px 5.25522px 5.25522px rgba(0, 0, 0, 0.25));
  pointer-events: none;
}
.you-know {
  position: absolute;
  /* width: 520px; */
  height: 78%;
  right: calc(50% - 14vh);
  bottom: 0px;
  transform: translate(50%, 0%);
  pointer-events: none;
}
.me {
  position: absolute;
  /* width: 600px; */
  height: 74%;
  /* left: calc(42% - 40vh); */
  right: calc(50% + 18vh);
  bottom: 0px;
  transform: translate(50%, 0%);
  pointer-events: none;
}
.img-cite {
  position: fixed;
  color: white;
  font-size: 12px;
  bottom: 0;
  left: 10px;
  z-index: 1;
}
</style>