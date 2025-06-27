<template>
    <div>
        <Number
            label="SubscriberId"
            v-model="value.subscriberId"
            :editMode="editMode"
        />
        <Number
            label="PublishId"
            v-model="value.publishId"
            :editMode="editMode"
        />
        <String
            label="Status"
            v-model="value.status"
            :editMode="editMode"
        />
        <Date
            label="ExpirationDate"
            v-model="value.expirationDate"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Subscribe',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "subscribes",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
